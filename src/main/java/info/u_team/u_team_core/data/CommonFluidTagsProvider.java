package info.u_team.u_team_core.data;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.core.Registry;

public abstract class CommonFluidTagsProvider extends CommonTagsProvider<Fluid> {
	
	@SuppressWarnings("deprecation")
	public CommonFluidTagsProvider(GenerationData data) {
		super(data, Registry.FLUID);
	}
	
	@Override
	protected String getTagFolder() {
		return "fluids";
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags";
	}
}
