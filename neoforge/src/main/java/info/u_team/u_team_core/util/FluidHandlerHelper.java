package info.u_team.u_team_core.util;

import net.neoforged.neoforge.fluids.FluidStack;

public class FluidHandlerHelper {
	
	public static boolean canFluidStacksStack(FluidStack stackA, FluidStack stackB) {
		if (stackA.isEmpty() || !(stackA.getFluid() == stackB.getFluid()) || stackA.hasTag() != stackB.hasTag()) {
			return false;
		}
		
		return (!stackA.hasTag() || stackA.getTag().equals(stackB.getTag()));
	}
	
	public static FluidStack copyStackWithSize(FluidStack stack, int size) {
		if (size == 0) {
			return FluidStack.EMPTY;
		}
		final FluidStack copy = stack.copy();
		copy.setAmount(size);
		return copy;
	}
	
}
