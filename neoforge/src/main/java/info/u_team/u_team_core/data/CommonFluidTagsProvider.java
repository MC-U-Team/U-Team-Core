package info.u_team.u_team_core.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;

public abstract class CommonFluidTagsProvider extends CommonTagsProvider<Fluid> {
	
	@SuppressWarnings("deprecation")
	public CommonFluidTagsProvider(GenerationData generationData) {
		super(generationData, Registries.FLUID, fluid -> fluid.builtInRegistryHolder().key());
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags: " + nameSuffix();
	}
	
}
