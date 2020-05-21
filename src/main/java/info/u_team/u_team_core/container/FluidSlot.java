package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.fluid.IFluidHandlerModifiable;
import net.minecraftforge.fluids.FluidStack;

public class FluidSlot {
	
	private final IFluidHandlerModifiable fluidHandler;
	private final int index;
	private final int x;
	private final int y;
	
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
