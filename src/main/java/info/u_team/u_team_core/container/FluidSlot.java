package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.InteractionType;
import info.u_team.u_team_core.api.fluid.IExtendedFluidHandler;
import net.minecraftforge.fluids.FluidStack;

public class FluidSlot {
	
	private final IExtendedFluidHandler fluidHandler;
	private final int index;
	public final int x;
	public final int y;
	
	public FluidSlot(IExtendedFluidHandler fluidHandler, int index, int x, int y) {
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
	
	public FluidStack insert(FluidStack stack) {
		return fluidHandler.insertFluid(index, stack, InteractionType.EXECUTE);
	}
	
	public FluidStack extract(int amount) {
		return fluidHandler.extractFluid(index, amount, InteractionType.EXECUTE);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
