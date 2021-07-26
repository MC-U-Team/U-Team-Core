package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.fluid.IFluidHandlerModifiable;
import info.u_team.u_team_core.inventory.UFluidStackHandler;
import net.minecraftforge.fluids.FluidStack;

public class FluidSlot {

	private final IFluidHandlerModifiable fluidHandler;
	private final int index;
	private final int x;
	private final int y;

	public int slotNumber;

	public FluidSlot(IFluidHandlerModifiable fluidHandler, int index, int x, int y) {
		this.fluidHandler = fluidHandler;
		this.index = index;
		this.x = x;
		this.y = y;
	}

	public boolean isFluidValid(FluidStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		return fluidHandler.isFluidValid(index, stack);
	}

	public FluidStack getStack() {
		return fluidHandler.getFluidInTank(index);
	}

	public void putStack(FluidStack stack) {
		fluidHandler.setFluidInTank(index, stack);
		onSlotChanged();
	}

	public void onSlotChanged() {
		if (fluidHandler instanceof UFluidStackHandler) {
			((UFluidStackHandler) fluidHandler).onContentsChanged(index);
		}
	}

	public int getSlotCapacity() {
		return fluidHandler.getTankCapacity(index);
	}

	public int getSlotCurrentyCapacity() {
		return getSlotCapacity() - getStack().getAmount();
	}

	public IFluidHandlerModifiable getFluidHandler() {
		return fluidHandler;
	}

	public int getIndex() {
		return index;
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
