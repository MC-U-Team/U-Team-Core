package info.u_team.u_team_core.menu;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.fluid.FluidHandlerModifiable;
import info.u_team.u_team_core.api.menu.FluidContainerListener;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.FluidSetAllContainerMessage;
import info.u_team.u_team_core.intern.network.FluidSetSlotContainerMessage;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

/**
 * Adds a management system for fluids in menus like items.
 * 
 * @author HyCraftHD
 */
public abstract class FluidContainerMenu extends UAbstractContainerMenu {
	
	protected final NonNullList<FluidStack> lastFluidSlots = NonNullList.create();
	public final List<FluidSlot> fluidSlots = NonNullList.create();
	private final NonNullList<FluidStack> remoteFluidSlots = NonNullList.create();
	
	/**
	 * Creates a container menu. Must be implemented by a sub class to be used.
	 * 
	 * @param menuType Menu type
	 * @param containerId Container id
	 */
	protected FluidContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
	}
	
	/**
	 * Adds a fluid slot to the menu. Should be called in the constructor of the container that implements the menu.
	 * 
	 * @param slot Fluid slot to add
	 * @return Same instance of the added fluid slot
	 */
	protected FluidSlot addFluidSlot(FluidSlot slot) {
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
	
	public NonNullList<FluidStack> getFluids() {
		final NonNullList<FluidStack> list = NonNullList.create();
		
		for (FluidSlot fluidSlot : fluidSlots) {
			list.add(fluidSlot.getStack());
		}
		return list;
	}
	
	// Called when a client clicks on a fluid slot
	
	public void fluidSlotClick(ServerPlayer player, int index, boolean shift, ItemStack clientClickStack) {
		final var serverClickStack = getCarried();
		
		// Check if an item is in the hand
		
		// Check if the client item is the same as the server item stack
		// Check if the slot index is in range to prevent server crashes with malicious packets
		if (serverClickStack.isEmpty() || !ItemStack.matches(clientClickStack, serverClickStack) || (index < 0 && index >= fluidSlots.size())) {
			return;
		}
		
		final var fluidSlot = fluidSlots.get(index);
		
		final Optional<FluidStack> containedFluidOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(serverClickStack, 1)).map(handler -> handler.drain(Integer.MAX_VALUE, FluidAction.SIMULATE));
		
		// Check if the item stack can hold fluids
		if (!containedFluidOptional.isPresent()) {
			return;
		}
		
		final var maximumTries = shift ? serverClickStack.getCount() : 1;
		
		if (!containedFluidOptional.orElseThrow(AssertionError::new).isEmpty()) {
			for (var i = 0; i < maximumTries; i++) {
				if (!insertFluidFromItem(player, fluidSlot, shift)) {
					break;
				}
			}
		} else {
			for (var i = 0; i < maximumTries; i++) {
				if (!extractFluidToItem(player, fluidSlot, shift)) {
					break;
				}
			}
		}
	}
	
	// TODO make this method better
	private boolean insertFluidFromItem(ServerPlayer player, FluidSlot fluidSlot, boolean shift) {
		
		final var playerInventory = new PlayerMainInvWrapper(player.getInventory());
		
		final var stack = getCarried();
		
		final var fluidHandlerOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));
		
		if (!fluidHandlerOptional.isPresent()) {
			return false;
		}
		
		final var handler = fluidHandlerOptional.orElseThrow(AssertionError::new);
		
		final var maxAmountToFill = fluidSlot.getSlotCurrentyCapacity();
		final var drainedFluidStack = handler.drain(maxAmountToFill, FluidAction.EXECUTE);
		
		if (drainedFluidStack.isEmpty() || !fluidSlot.isFluidValid(drainedFluidStack)) {
			return false;
		}
		
		final var slotEmpty = fluidSlot.getStack().isEmpty();
		
		if (!slotEmpty && !drainedFluidStack.isFluidEqual(fluidSlot.getStack())) {
			return false;
		}
		
		final var outputStack = handler.getContainer();
		
		if (stack.getCount() == 1 && !shift) {
			if (slotEmpty) {
				fluidSlot.putStack(drainedFluidStack);
			} else {
				fluidSlot.getStack().grow(drainedFluidStack.getAmount());
				fluidSlot.setChanged();
			}
			setCarried(outputStack);
		} else {
			if (ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, true).isEmpty()) {
				if (slotEmpty) {
					fluidSlot.putStack(drainedFluidStack);
				} else {
					fluidSlot.getStack().grow(drainedFluidStack.getAmount());
					fluidSlot.setChanged();
				}
				ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, false);
				stack.shrink(1);
				if (stack.isEmpty()) {
					setCarried(ItemStack.EMPTY);
				}
			}
		}
		player.connection.send(new ClientboundContainerSetSlotPacket(-1, -1, 0, getCarried())); // TODO what is state id? Third parameter 0
		return true;
	}
	
	// TODO make this method better (maybe extract to an other class??)
	private boolean extractFluidToItem(ServerPlayer player, FluidSlot fluidSlot, boolean shift) {
		
		final var playerInventory = new PlayerMainInvWrapper(player.getInventory());
		
		final var stack = getCarried();
		
		final var fluidHandlerOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));
		
		if (!fluidHandlerOptional.isPresent()) {
			return false;
		}
		
		final var handler = fluidHandlerOptional.orElseThrow(AssertionError::new);
		
		final var amountFilled = handler.fill(fluidSlot.getStack(), FluidAction.EXECUTE);
		
		if (amountFilled <= 0) {
			return false;
		}
		
		final var outputStack = handler.getContainer();
		
		if (stack.getCount() == 1 && !shift) {
			fluidSlot.getStack().shrink(amountFilled);
			if (fluidSlot.getStack().isEmpty()) {
				fluidSlot.putStack(FluidStack.EMPTY);
			} else {
				fluidSlot.setChanged();
			}
			setCarried(outputStack);
		} else {
			if (ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, true).isEmpty()) {
				fluidSlot.getStack().shrink(amountFilled);
				if (fluidSlot.getStack().isEmpty()) {
					fluidSlot.putStack(FluidStack.EMPTY);
				} else {
					fluidSlot.setChanged();
				}
				ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, false);
				stack.shrink(1);
				if (stack.isEmpty()) {
					setCarried(ItemStack.EMPTY);
				}
			}
		}
		player.connection.send(new ClientboundContainerSetSlotPacket(-1, -1, 0, getCarried())); // TODO what is state id? Third parameter 0
		return true;
	}
	
	// Send packets for client sync
	
	/**
	 * Sends all menu data to the client.
	 */
	@Override
	public void sendAllDataToRemote() {
		super.sendAllDataToRemote();
		
		for (var slot = 0; slot < fluidSlots.size(); slot++) {
			remoteFluidSlots.set(slot, fluidSlots.get(slot).getStack().copy());
		}
		
		if (getSynchronizerPlayer() != null) {
			UCoreNetwork.NETWORK.send(PacketDistributor.PLAYER.with(this::getSynchronizerPlayer), new FluidSetAllContainerMessage(containerId, incrementStateId(), remoteFluidSlots));
		}
	}
	
	/**
	 * Broadcast changed data
	 */
	@Override
	public void broadcastChanges() {
		for (var slot = 0; slot < fluidSlots.size(); slot++) {
			final var stack = fluidSlots.get(slot).getStack();
			final var supplier = Suppliers.memoize(stack::copy);
			triggerFluidSlotListeners(slot, stack, supplier);
			synchronizeFluidSlotToRemote(slot, stack, supplier);
		}
		super.broadcastChanges();
	}
	
	/**
	 * Broadcast all data
	 */
	@Override
	public void broadcastFullState() {
		for (var slot = 0; slot < fluidSlots.size(); slot++) {
			final var stack = fluidSlots.get(slot).getStack();
			triggerFluidSlotListeners(slot, stack, stack::copy);
		}
		super.broadcastFullState();
	}
	
	/**
	 * Trigger the listeners for fluid slot changes and update internal slot state.
	 * 
	 * @param slotId Slot id
	 * @param stack Fluid stack
	 * @param supplier Supplier to get a copy of the fluid stack
	 */
	private void triggerFluidSlotListeners(int slotId, FluidStack stack, Supplier<FluidStack> supplier) {
		final var lastStack = lastFluidSlots.get(slotId);
		if (!lastStack.isFluidStackIdentical(stack)) {
			final var copy = supplier.get();
			lastFluidSlots.set(slotId, copy);
			
			for (ContainerListener listener : containerListeners) {
				if (listener instanceof FluidContainerListener fluidListener) {
					fluidListener.fluidSlotChanged(this, slotId, copy);
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
		if (!suppressRemoteUpdates) {
			final var remoteStack = remoteFluidSlots.get(slotId);
			if (!remoteStack.isFluidStackIdentical(stack)) {
				final var copy = supplier.get();
				remoteFluidSlots.set(slotId, copy);
				
				if (getSynchronizerPlayer() != null) {
					UCoreNetwork.NETWORK.send(PacketDistributor.PLAYER.with(this::getSynchronizerPlayer), new FluidSetSlotContainerMessage(containerId, incrementStateId(), slotId, copy));
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
	
	// Used for sync with the client
	
	/**
	 * Sets a fluid slot on the client side
	 * 
	 * @param slotId Slot id
	 * @param stateId State id
	 * @param stack Fluid stack
	 */
	public void setFluid(int slotId, int stateId, FluidStack stack) {
		getFluidSlot(slotId).putStack(stack);
		this.stateId = stateId;
	}
	
	/**
	 * Sets the fluid slots on the client side
	 * 
	 * @param stateId State id
	 * @param stacks Fluid stacks
	 */
	public void initializeFluidContents(int stateId, List<FluidStack> stacks) {
		for (var index = 0; index < stacks.size(); index++) {
			getFluidSlot(index).putStack(stacks.get(index));
		}
		this.stateId = stateId;
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
	protected void appendFluidInventory(FluidHandlerModifiable handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendFluidInventory(handler, 0, inventoryHeight, inventoryWidth, x, y);
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
	protected void appendFluidInventory(FluidHandlerModifiable handler, FluidSlotHandlerFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendFluidInventory(handler, function, 0, inventoryHeight, inventoryWidth, x, y);
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
	protected void appendFluidInventory(FluidHandlerModifiable handler, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendFluidInventory(handler, FluidSlot::new, startIndex, inventoryHeight, inventoryWidth, x, y);
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
	protected void appendFluidInventory(FluidHandlerModifiable handler, FluidSlotHandlerFunction function, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (var height = 0; height < inventoryHeight; height++) {
			for (var width = 0; width < inventoryWidth; width++) {
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
}
