package info.u_team.u_team_core.container;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.api.fluid.IFluidHandlerModifiable;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.FluidSetAllContainerMessage;
import info.u_team.u_team_core.intern.network.FluidSetSlotContainerMessage;
import net.minecraft.core.NonNullList;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

public abstract class FluidContainer extends AbstractContainerMenu {

	private final NonNullList<FluidStack> fluidStacks = NonNullList.create();
	public final List<FluidSlot> fluidSlots = Lists.newArrayList();

	public FluidContainer(MenuType<?> type, int id) {
		super(type, id);
	}

	protected FluidSlot addFluidSlot(FluidSlot slot) {
		slot.slotNumber = fluidSlots.size();
		fluidSlots.add(slot);
		fluidStacks.add(FluidStack.EMPTY);
		return slot;
	}

	public FluidSlot getFluidSlot(int slot) {
		return fluidSlots.get(slot);
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
		final ItemStack serverClickStack = getCarried();

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

		final ItemStack stack = getCarried();

		final LazyOptional<IFluidHandlerItem> fluidHandlerOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));

		if (!fluidHandlerOptional.isPresent()) {
			return false;
		}

		final IFluidHandlerItem handler = fluidHandlerOptional.orElseThrow(AssertionError::new);

		final int maxAmountToFill = fluidSlot.getSlotCurrentyCapacity();
		final FluidStack drainedFluidStack = handler.drain(maxAmountToFill, FluidAction.EXECUTE);

		if (drainedFluidStack.isEmpty() || !fluidSlot.isFluidValid(drainedFluidStack)) {
			return false;
		}

		final boolean slotEmpty = fluidSlot.getStack().isEmpty();

		if (!slotEmpty && !drainedFluidStack.isFluidEqual(fluidSlot.getStack())) {
			return false;
		}

		final ItemStack outputStack = handler.getContainer();

		if (stack.getCount() == 1 && !shift) {
			if (slotEmpty) {
				fluidSlot.putStack(drainedFluidStack);
			} else {
				fluidSlot.getStack().grow(drainedFluidStack.getAmount());
				fluidSlot.onSlotChanged();
			}
			setCarried(outputStack);
		} else {
			if (ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, true).isEmpty()) {
				if (slotEmpty) {
					fluidSlot.putStack(drainedFluidStack);
				} else {
					fluidSlot.getStack().grow(drainedFluidStack.getAmount());
					fluidSlot.onSlotChanged();
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

		final PlayerMainInvWrapper playerInventory = new PlayerMainInvWrapper(player.getInventory());

		final ItemStack stack = getCarried();

		final LazyOptional<IFluidHandlerItem> fluidHandlerOptional = FluidUtil.getFluidHandler(ItemHandlerHelper.copyStackWithSize(stack, 1));

		if (!fluidHandlerOptional.isPresent()) {
			return false;
		}

		final IFluidHandlerItem handler = fluidHandlerOptional.orElseThrow(AssertionError::new);

		final int amountFilled = handler.fill(fluidSlot.getStack(), FluidAction.EXECUTE);

		if (amountFilled <= 0) {
			return false;
		}

		final ItemStack outputStack = handler.getContainer();

		if (stack.getCount() == 1 && !shift) {
			fluidSlot.getStack().shrink(amountFilled);
			if (fluidSlot.getStack().isEmpty()) {
				fluidSlot.putStack(FluidStack.EMPTY);
			} else {
				fluidSlot.onSlotChanged();
			}
			setCarried(outputStack);
		} else {
			if (ItemHandlerHelper.insertItemStacked(playerInventory, outputStack, true).isEmpty()) {
				fluidSlot.getStack().shrink(amountFilled);
				if (fluidSlot.getStack().isEmpty()) {
					fluidSlot.putStack(FluidStack.EMPTY);
				} else {
					fluidSlot.onSlotChanged();
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

	// Used for sync with the client

	public void setFluidStackInSlot(int slot, FluidStack stack) {
		getFluidSlot(slot).putStack(stack);
	}

	public void setAllFluidSlots(List<FluidStack> list) {
		for (int index = 0; index < list.size(); index++) {
			getFluidSlot(index).putStack(list.get(index));
		}
	}

	// Send packets for client sync

	@Override
	public void addSlotListener(ContainerListener listener) {
		super.addSlotListener(listener);
		if (listener instanceof ServerPlayer) {
			UCoreNetwork.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) listener), new FluidSetAllContainerMessage(containerId, getFluids()));
		}
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		for (int index = 0; index < fluidSlots.size(); index++) {
			final FluidStack stackSlot = fluidSlots.get(index).getStack();
			final FluidStack stackSynced = fluidStacks.get(index);
			if (!stackSynced.isFluidStackIdentical(stackSlot)) {
				final FluidStack stackNewSynced = stackSlot.copy();
				fluidStacks.set(index, stackNewSynced);

				final List<Connection> networkManagers = containerListeners.stream() //
						.filter(listener -> listener instanceof ServerPlayer) //
						.map(listener -> ((ServerPlayer) listener).connection.getConnection()) //
						.collect(Collectors.toList());

				UCoreNetwork.NETWORK.send(PacketDistributor.NMLIST.with(() -> networkManagers), new FluidSetSlotContainerMessage(containerId, index, stackNewSynced));
			}
		}
	}

	/**
	 * This methods can add any {@link IFluidHandlerModifiable} to the container. You can specialize the inventory height
	 * (slot rows) and width (slot columns).
	 *
	 * @param handler Some fluid handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendFluidInventory(IFluidHandlerModifiable handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendFluidInventory(handler, 0, inventoryHeight, inventoryWidth, x, y);
	}

	/**
	 * This methods can add any {@link IFluidHandlerModifiable} to the container. You can specialize the inventory height
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
	protected void appendFluidInventory(IFluidHandlerModifiable handler, FluidSlotHandlerFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendFluidInventory(handler, function, 0, inventoryHeight, inventoryWidth, x, y);
	}

	/**
	 * This methods can add any {@link IFluidHandlerModifiable} to the container. You can specialize the inventory height
	 * (slot rows) and width (slot columns).
	 *
	 * @param handler Some fluid handler
	 * @param startIndex Start index of the handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendFluidInventory(IFluidHandlerModifiable handler, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendFluidInventory(handler, FluidSlot::new, startIndex, inventoryHeight, inventoryWidth, x, y);
	}

	/**
	 * This methods can add any {@link IFluidHandlerModifiable} to the container. You can specialize the inventory height
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
	protected void appendFluidInventory(IFluidHandlerModifiable handler, FluidSlotHandlerFunction function, int startIndex, int inventoryHeight, int inventoryWidth, int x, int y) {
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
		FluidSlot getSlot(IFluidHandlerModifiable fluidHandler, int index, int xPosition, int yPosition);
	}
}
