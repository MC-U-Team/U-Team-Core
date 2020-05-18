package info.u_team.u_team_core.container;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.*;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.PacketDistributor;

public abstract class FluidContainer extends Container {
	
	private final NonNullList<FluidStack> fluidStacks = NonNullList.create();
	public final List<FluidSlot> fluidSlots = Lists.newArrayList();
	
	public FluidContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	protected FluidSlot addFluidSlot(FluidSlot slot) {
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
}
