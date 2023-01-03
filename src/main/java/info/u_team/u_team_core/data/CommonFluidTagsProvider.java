package info.u_team.u_team_core.data;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonFluidTagsProvider extends CommonTagsProvider<Fluid> {
	
	public CommonFluidTagsProvider(GenerationData generationData) {
		super(generationData, ForgeRegistries.Keys.FLUIDS);
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags";
	}
	
}
