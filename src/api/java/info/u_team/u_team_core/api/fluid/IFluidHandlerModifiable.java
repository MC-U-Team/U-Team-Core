package info.u_team.u_team_core.api.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidHandlerModifiable extends IFluidHandler {

	void setFluidInTank(int tank, FluidStack stack);

}
