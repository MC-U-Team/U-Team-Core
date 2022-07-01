package info.u_team.u_team_core.data;

import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;

public abstract class CommonFluidTagsProvider extends CommonTagsProvider<Fluid> {
	
	@SuppressWarnings("deprecation")
	public CommonFluidTagsProvider(GenerationData generationData) {
		super(generationData, Registry.FLUID);
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags";
	}
	
}
