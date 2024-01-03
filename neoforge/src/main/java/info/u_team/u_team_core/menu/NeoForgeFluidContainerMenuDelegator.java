package info.u_team.u_team_core.menu;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.fluid.FluidHandlerModifiable;
import info.u_team.u_team_core.api.menu.FluidContainerListener;
import info.u_team.u_team_core.intern.network.ContainerSetFluidContentMessage;
import info.u_team.u_team_core.intern.network.ContainerSetFluidSlotMessage;
import info.u_team.u_team_core.menu.FluidContainerMenu.FluidContainerDelegator;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;
import net.neoforged.neoforge.network.PacketDistributor;

// Very unstable api, will be reworked
public class NeoForgeFluidContainerMenuDelegator implements FluidContainerDelegator {
	
	private final FluidContainerMenu menu;
	
	NeoForgeFluidContainerMenuDelegator(FluidContainerMenu menu) {
		this.menu = menu;
	}
	
	private final NonNullList<FluidStack> lastFluidSlots = NonNullList.create();
	public final List<FluidSlot> fluidSlots = NonNullList.create();
	private final NonNullList<FluidStack> remoteFluidSlots = NonNullList.create();
	
	/**
	 * Adds a fluid slot to the menu. Should be called in the constructor of the container that implements the menu.
	 *
	 * @param slot Fluid slot to add
	 * @return Same instance of the added fluid slot
	 */
	public FluidSlot addFluidSlot(FluidSlot slot) {
		slot.index = fluidSlots.size();
		fluidSlots.add(slot);
		lastFluidSlots.add(FluidStack.EMPTY);
		remoteFluidSlots.add(FluidStack.EMPTY);
		return slot;
	}
	
	/**
	 * Returns the fluid slot for a give slot id. Throws exception if the id is out of bounds.
	 *
	 * @param slotId Slot id
	 * @return Fluid slot with that id
	 */
	public FluidSlot getFluidSlot(int slotId) {
		return fluidSlots.get(slotId);
	}
	
	// Called when a client clicks on a fluid slot
	
	public void fluidSlotClick(ServerPlayer player, int index, boolean shift, ItemStack clientClickStack) {
		final ItemStack serverClickStack = menu.getCarried();
		
		// Check if an item is in the hand
		
		// Check if the client item is the same as the server item stack
		// Check if the slot index is in range to prevent server crashes with malicious packets
		if (serverClickStack.isEmpty() || !ItemStack.matches(clientClickStack, serverClickStack) || (index < 0 && index >= fluidSlots.size())) {
			return;
		}
		
		final FluidSlot fluidSlot = fluidSlots.get(index);
		
		final Optional<FluidStack> containedFluidOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(serverClickStack, 1)).map(handler -> handler.drain(Integer.MAX_VALUE, FluidAction.SIMULATE));
		
		// Check if the item stack can hold fluids
		if (!containedFluidOptional.isPresent()) {
			return;
		}
		
		final int maximumTries = shift ? serverClickStack.getCount() : 1;
		
		if (!containedFluidOptional.orElseThrow(AssertionError::new).isEmpty()) {
			for (int i = 0; i < maximumTries; i++) {
				if (!insertFluidFromItem(player, fluidSlot, shift)) {
					break;
				}
			}
		} else {
			for (int i = 0; i < maximumTries; i++) {
				if (!extractFluidToItem(player, fluidSlot, shift)) {
					break;
				}
			}
		}
	}
	
	// TODO make this method better
	private boolean insertFluidFromItem(ServerPlayer player, FluidSlot fluidSlot, boolean shift) {
		
		final PlayerMainInvWrapper playerInventory = new PlayerMainInvWrapper(player.getInventory());
		
		final ItemStack stack = menu.getCarried();
		
		final Optional<IFluidHandlerItem> fluidHandlerOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));
		
		if (!fluidHandlerOptional.isPresent()) {
			return false;
		}
		
		final IFluidHandlerItem handler = fluidHandlerOptional.orElseThrow(AssertionError::new);
		
		final int maxAmountToFill = fluidSlot.getRemainingSlotCapacity();
		final FluidStack drainedFluidStack = handler.drain(maxAmountToFill, FluidAction.EXECUTE);
		
		if (drainedFluidStack.isEmpty() || !fluidSlot.mayPlace(drainedFluidStack)) {
			return false;
		}
		
		final boolean slotEmpty = fluidSlot.getFluid().isEmpty();
		
		if (!slotEmpty && !drainedFluidStack.isFluidEqual(fluidSlot.getFluid())) {
			return false;
		}
		
		final ItemStack outputStack = handler.getContainer();
		
		if (stack.getCount() == 1 && !shift) {
			if (slotEmpty) {
				fluidSlot.set(drainedFluidStack);
			} else {
				fluidSlot.getFluid().grow(drainedFluidStack.getAmount());
				fluidSlot.setChanged();
			}
			menu.setCarried(outputStack);
		} else {
			if (ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, true).isEmpty()) {
				if (slotEmpty) {
					fluidSlot.set(drainedFluidStack);
				} else {
					fluidSlot.getFluid().grow(drainedFluidStack.getAmount());
					fluidSlot.setChanged();
				}
				ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, false);
				stack.shrink(1);
				if (stack.isEmpty()) {
					menu.setCarried(ItemStack.EMPTY);
				}
			}
		}
		// player.connection.send(new ClientboundContainerSetSlotPacket(-1, -1, 0, getCarried())); // TODO what is state id?
		// Third parameter 0
		return true;
	}
	
	// TODO make this method better (maybe extract to an other class??)
	private boolean extractFluidToItem(ServerPlayer player, FluidSlot fluidSlot, boolean shift) {
		
		final PlayerMainInvWrapper playerInventory = new PlayerMainInvWrapper(player.getInventory());
		
		final ItemStack stack = menu.getCarried();
		
		final Optional<IFluidHandlerItem> fluidHandlerOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));
		
		if (!fluidHandlerOptional.isPresent()) {
			return false;
		}
		
		final IFluidHandlerItem handler = fluidHandlerOptional.orElseThrow(AssertionError::new);
		
		final int amountFilled = handler.fill(fluidSlot.getFluid(), FluidAction.EXECUTE);
		
		if (amountFilled <= 0) {
			return false;
		}
		
		final ItemStack outputStack = handler.getContainer();
		
		if (stack.getCount() == 1 && !shift) {
			fluidSlot.getFluid().shrink(amountFilled);
			if (fluidSlot.getFluid().isEmpty()) {
				fluidSlot.set(FluidStack.EMPTY);
			} else {
				fluidSlot.setChanged();
			}
			menu.setCarried(outputStack);
		} else {
			if (ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, true).isEmpty()) {
				fluidSlot.getFluid().shrink(amountFilled);
				if (fluidSlot.getFluid().isEmpty()) {
					fluidSlot.set(FluidStack.EMPTY);
				} else {
					fluidSlot.setChanged();
				}
				ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, false);
				stack.shrink(1);
				if (stack.isEmpty()) {
					menu.setCarried(ItemStack.EMPTY);
				}
			}
		}
		// player.connection.send(new ClientboundContainerSetSlotPacket(-1, -1, 0, getCarried())); // TODO what is state id?
		// Third parameter 0
		return true;
	}
	
	/**
	 * Sends all menu data to the client.
	 */
	@Override
	public void sendAllDataToRemote() {
		for (int slot = 0; slot < fluidSlots.size(); slot++) {
			remoteFluidSlots.set(slot, fluidSlots.get(slot).getFluid().copy());
		}
		
		if (menu.getSynchronizerPlayer() != null) {
			PacketDistributor.PLAYER.with(menu.getSynchronizerPlayer()).send(new ContainerSetFluidContentMessage(menu.containerId, menu.incrementStateId(), remoteFluidSlots));
		}
	}
	
	/**
	 * Broadcast changed data
	 */
	@Override
	public void broadcastChanges() {
		for (int slot = 0; slot < fluidSlots.size(); slot++) {
			final FluidStack stack = fluidSlots.get(slot).getFluid();
			final Supplier<FluidStack> supplier = Suppliers.memoize(stack::copy);
			triggerFluidSlotListeners(slot, stack, supplier);
			synchronizeFluidSlotToRemote(slot, stack, supplier);
		}
	}
	
	/**
	 * Broadcast all data
	 */
	@Override
	public void broadcastFullState() {
		for (int slot = 0; slot < fluidSlots.size(); slot++) {
			final FluidStack stack = fluidSlots.get(slot).getFluid();
			triggerFluidSlotListeners(slot, stack, stack::copy);
		}
	}
	
	/**
	 * Trigger the listeners for fluid slot changes and update internal slot state.
	 *
	 * @param slotId Slot id
	 * @param stack Fluid stack
	 * @param supplier Supplier to get a copy of the fluid stack
	 */
	private void triggerFluidSlotListeners(int slotId, FluidStack stack, Supplier<FluidStack> supplier) {
		final FluidStack lastStack = lastFluidSlots.get(slotId);
		if (!lastStack.isFluidStackIdentical(stack)) {
			final FluidStack copy = supplier.get();
			lastFluidSlots.set(slotId, copy);
			
			for (final ContainerListener listener : menu.containerListeners) {
				if (listener instanceof final FluidContainerListener fluidListener) {
					fluidListener.fluidSlotChanged(menu, slotId, copy);
				}
			}
		}
	}
	
	/**
	 * Synchronize the slot to the client and update internal state.
	 *
	 * @param slotId Slot id
	 * @param stack Fluid stack
	 * @param supplier Supplier the get a copy the fluid stack
	 */
	private void synchronizeFluidSlotToRemote(int slotId, FluidStack stack, Supplier<FluidStack> supplier) {
		if (!menu.suppressRemoteUpdates) {
			final FluidStack remoteStack = remoteFluidSlots.get(slotId);
			if (!remoteStack.isFluidStackIdentical(stack)) {
				final FluidStack copy = supplier.get();
				remoteFluidSlots.set(slotId, copy);
				
				if (menu.getSynchronizerPlayer() != null) {
					PacketDistributor.PLAYER.with(menu.getSynchronizerPlayer()).send(new ContainerSetFluidSlotMessage(menu.containerId, menu.incrementStateId(), slotId, copy));
				}
			}
		}
	}
	
	/**
	 * Set the fluid stack that should be in the remote fluid slot. Copies the stack.
	 *
	 * @param slotId Slot id
	 * @param stack Fluid stack
	 */
	public void setRemoteFluidSlot(int slotId, FluidStack stack) {
		remoteFluidSlots.set(slotId, stack.copy());
	}
	
	/**
	 * Set the fluid stack that should be in the remote fluid slot. Doesn't copy the stack.
	 *
	 * @param slotId Slot id
	 * @param stack Fluid stack
	 */
	public void setRemoteFluidSlotNoCopy(int slotId, FluidStack stack) {
		remoteFluidSlots.set(slotId, stack);
	}
	
	/**
	 * Sets a fluid slot on the client side
	 *
	 * @param slotId Slot id
	 * @param stateId State id
	 * @param stack Fluid stack
	 */
	public void setFluid(int slotId, int stateId, FluidStack stack) {
		getFluidSlot(slotId).set(stack);
		menu.stateId = stateId;
	}
	
	/**
	 * Sets the fluid slots on the client side
	 *
	 * @param stateId State id
	 * @param stacks Fluid stacks
	 */
	public void initializeFluidContents(int stateId, List<FluidStack> stacks) {
		for (int index = 0; index < stacks.size(); index++) {
			getFluidSlot(index).set(stacks.get(index));
		}
		menu.stateId = stateId;
	}
	
	/**
	 * Returns the last slot list that is used to check if a fluid stack has changed since last check. The list should not
	 * be modified manually.
	 *
	 * @return List with fluid stacks
	 */
	public List<FluidStack> getLastFluidSlots() {
		return lastFluidSlots;
	}
	
	/**
	 * This methods can add any {@link FluidHandlerModifiable} to the container. You can specialize the inventory height
	 * (slot rows) and width (slot columns).
	 *
	 * @param handler Some fluid handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void addFluidSlots(FluidHandlerModifiable handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		addFluidSlots(handler, 0, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link FluidHandlerModifiable} to the container. You can specialize the inventory height
	 * (slot rows) and width (slot columns). You must supplier a function that create a fluid slot. With this you can set
	 * your own slot. implementations.
	 *
	 * @param handler Some fluid handler
	 * @param function Function to create a fluid slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void addFluidSlots(FluidHandlerModifiable handler, FluidSlotHandlerFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		addFluidSlots(handler, function, 0, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link FluidHandlerModifiable} to the container. You can specialize the inventory height
	 * (slot rows) and width (slot columns).
	 *
	 * @param handler Some fluid handler
	 * @param startIndex Start index of the handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void addFluidSlots(FluidHandlerModifiable handler, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		addFluidSlots(handler, FluidSlot::new, startIndex, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link FluidHandlerModifiable} to the container. You can specialize the inventory height
	 * (slot rows) and width (slot columns). You must supplier a function that create a fluid slot. With this you can set
	 * your own slot. implementations.
	 *
	 * @param handler Some fluid handler
	 * @param function Function to create a fluid slot.
	 * @param startIndex Start index of the handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void addFluidSlots(FluidHandlerModifiable handler, FluidSlotHandlerFunction function, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (int height = 0; height < inventoryHeight; height++) {
			for (int width = 0; width < inventoryWidth; width++) {
				addFluidSlot(function.getSlot(handler, startIndex + (width + height * inventoryWidth), width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * Used as a function to customize fluid slots with the append methods
	 *
	 * @author HyCraftHD
	 */
	@FunctionalInterface
	public static interface FluidSlotHandlerFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 *
		 * @param fluidHandler A fluid handler
		 * @param index Index for this fluid handler
		 * @param xPosition x coordinate
		 * @param yPosition y coordinate
		 * @return A Slot instance
		 */
		FluidSlot getSlot(FluidHandlerModifiable fluidHandler, int index, int xPosition, int yPosition);
	}
	
	public static class Factory implements FluidContainerDelegator.Factory {
		
		@Override
		public FluidContainerDelegator create(FluidContainerMenu menu) {
			return new NeoForgeFluidContainerMenuDelegator(menu);
		}
	}
}
