package info.u_team.u_team_core.container;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.inventory.container.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

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
}
