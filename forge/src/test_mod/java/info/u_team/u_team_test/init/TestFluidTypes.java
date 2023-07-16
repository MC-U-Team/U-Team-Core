package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_test.TestMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;

public class TestFluidTypes {
	
	public static final CommonRegister<FluidType> FLUID_TYPES = CommonRegister.create(ForgeRegistries.Keys.FLUID_TYPES, TestMod.MODID);
	
	public static void register() {
		FLUID_TYPES.register();
	}
	
}
