package info.u_team.u_team_core.container;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.api.fluid.IFluidHandlerModifiable;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fml.network.PacketDistributor;

public abstract class FluidContainer extends Container {
	
	private final NonNullList<FluidStack> fluidStacks = NonNullList.create();
	public final List<FluidSlot> fluidSlots = Lists.newArrayList();
	
	public FluidContainer(ContainerType<?> type, int id) {
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
		
		for (int index = 0; index < fluidSlots.size(); index++) {
			list.add(fluidSlots.get(index).getStack());
		}
		return list;
	}
	
	// Called when a client clicks on a fluid slot
	
	public void fluidSlotClick(ServerPlayerEntity player, int index, ItemStack clientClickStack) {
		final ItemStack serverClickStack = player.inventory.getItemStack();
		
		// Check if the client item is the same as the server item stack
		if (!ItemStack.areItemStacksEqual(clientClickStack, serverClickStack)) {
			return;
		}
		
		// Check if the slot index is in range to prevent server crashes with malicious packets
		if (index < 0 && index >= fluidSlots.size()) {
			return;
		}
		
		final FluidSlot fluidSlot = getFluidSlot(index);
		final FluidStack fluidStack = fluidSlot.getStack();
		
		final LazyOptional<FluidStack> containedFluidStackOptional = FluidUtil.getFluidContained(serverClickStack);
		
		if (!containedFluidStackOptional.isPresent()) {
			return;
		}
		
		final FluidStack containedFluidStack = containedFluidStackOptional.orElseThrow(AssertionError::new);
		
		if (!containedFluidStack.isEmpty()) {
			if (!fluidStack.isEmpty() && !containedFluidStack.isFluidEqual(fluidStack)) {
				return;
			}
			final int maxDrain = fluidSlot.getSlotCapacity() - fluidStack.getAmount();
			final IFluidHandlerItem containedFluidHandler = FluidUtil.getFluidHandler(serverClickStack).orElseThrow(AssertionError::new);
			final FluidStack drainedFluidStack = containedFluidHandler.drain(maxDrain, FluidAction.EXECUTE);
			
			if (!drainedFluidStack.isEmpty()) {
				fluidStack.grow(drainedFluidStack.getAmount());
				// TODO mark dirty methods
				player.inventory.setItemStack(containedFluidHandler.getContainer());
			}
			
		} else {
			
		}
		
		// FluidActionResult res = FluidUtil.tryEmptyContainer(itemStack, fluidSlot.getFluidHandler(), 64_000, player, true);
		//
		// if (res.isSuccess())
		// player.inventory.setItemStack(res.getResult());
		
		// if (fluidStack.isEmpty()) {
		// // FILL slot
		// FluidUtil.getFluidContained(itemStack) //
		// .filter(fluidSlot::isFluidValid) //
		// .filter(toInsert -> fluidStack.getAmount() + toInsert.getAmount() <= fluidSlot.getSlotCapacity()).ifPresent(toInsert
		// -> {
		// fluidSlot.putStack(toInsert);
		// });
		// player.inventory.setItemStack(FluidUtil.getFluidHandler(itemStack).map(handler -> {
		// handler.drain(Integer.MAX_VALUE, FluidAction.EXECUTE);
		// return handler;
		// }).map(IFluidHandlerItem::getContainer).orElse(ItemStack.EMPTY));
		// } else {
		// // EMPTY slot
		// }
		
		player.connection.sendPacket(new SSetSlotPacket(-1, -1, player.inventory.getItemStack()));
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
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (listener instanceof ServerPlayerEntity) {
			UCoreNetwork.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) listener), new FluidSetAllContainerMessage(windowId, getFluids()));
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int index = 0; index < fluidSlots.size(); index++) {
			final FluidStack stackSlot = fluidSlots.get(index).getStack();
			final FluidStack stackSynced = fluidStacks.get(index);
			if (!stackSynced.isFluidStackIdentical(stackSlot)) {
				final FluidStack stackNewSynced = stackSlot.copy();
				fluidStacks.set(index, stackNewSynced);
				
				final List<NetworkManager> networkManagers = listeners.stream() //
						.filter(listener -> listener instanceof ServerPlayerEntity) //
						.map(listener -> ((ServerPlayerEntity) listener).connection.getNetworkManager()) //
						.collect(Collectors.toList());
				
				UCoreNetwork.NETWORK.send(PacketDistributor.NMLIST.with(() -> networkManagers), new FluidSetSlotContainerMessage(windowId, index, stackNewSynced));
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
		appendFluidInventory(handler, FluidSlot::new, inventoryHeight, inventoryWidth, x, y);
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
		for (int height = 0; height < inventoryHeight; height++) {
			for (int width = 0; width < inventoryWidth; width++) {
				addFluidSlot(function.getSlot(handler, width + height * inventoryWidth, width * 18 + x, height * 18 + y));
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
