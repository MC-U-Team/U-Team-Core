package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.fluid.FluidHandlerModifiable;
import info.u_team.u_team_core.inventory.UFluidStackHandler;
import net.minecraftforge.fluids.FluidStack;

public class FluidSlot {
	
	private final FluidHandlerModifiable fluidHandler;
	private final int slot;
	private final int x;
	private final int y;
	
	public int index;
	
	public FluidSlot(FluidHandlerModifiable fluidHandler, int slot, int x, int y) {
		this.fluidHandler = fluidHandler;
		this.slot = slot;
		this.x = x;
		this.y = y;
	}
	
	public boolean isFluidValid(FluidStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		return fluidHandler.isFluidValid(slot, stack);
	}
	
	public FluidStack getStack() {
		return fluidHandler.getFluidInTank(slot);
	}
	
	public void putStack(FluidStack stack) {
		fluidHandler.setFluidInTank(slot, stack);
		onSlotChanged();
	}
	
	public void onSlotChanged() {
		if (fluidHandler instanceof UFluidStackHandler) {
			((UFluidStackHandler) fluidHandler).onContentsChanged(slot);
		}
	}
	
	public int getSlotCapacity() {
		return fluidHandler.getTankCapacity(slot);
	}
	
	public int getSlotCurrentyCapacity() {
		return getSlotCapacity() - getStack().getAmount();
	}
	
	public FluidHandlerModifiable getFluidHandler() {
		return fluidHandler;
	}
	
	public int getSlotIndex() {
		return slot;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isEnabled() {
		return true;
	}
}
