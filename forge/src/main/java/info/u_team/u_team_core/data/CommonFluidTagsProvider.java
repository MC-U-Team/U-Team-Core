package info.u_team.u_team_core.data;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonFluidTagsProvider extends CommonTagsProvider<Fluid> {
	
	@SuppressWarnings("deprecation")
	public CommonFluidTagsProvider(GenerationData generationData) {
		super(generationData, ForgeRegistries.Keys.FLUIDS, fluid -> fluid.builtInRegistryHolder().key());
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags: " + nameSuffix();
	}
	
}
