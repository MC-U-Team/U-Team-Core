package info.u_team.u_team_core.api.fluid;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public interface FluidHandlerModifiable extends IFluidHandler {
	
	void setFluidInTank(int tank, FluidStack stack);
	
}
