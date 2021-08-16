package info.u_team.u_team_core.util;

import net.minecraftforge.fluids.FluidStack;

public class FluidHandlerHelper {
	
	public static boolean canFluidStacksStack(FluidStack a, FluidStack b) {
		if (a.isEmpty() || !(a.getFluid() == b.getFluid()) || a.hasTag() != b.hasTag()) {
			return false;
		}
		
		return (!a.hasTag() || a.getTag().equals(b.getTag()));
	}
	
	public static FluidStack copyStackWithSize(FluidStack stack, int size) {
		if (size == 0) {
			return FluidStack.EMPTY;
		}
		final var copy = stack.copy();
		copy.setAmount(size);
		return copy;
	}
	
}
