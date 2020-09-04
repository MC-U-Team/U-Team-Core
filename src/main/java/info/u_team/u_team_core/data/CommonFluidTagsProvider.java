package info.u_team.u_team_core.data;

import java.nio.file.Path;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

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
	protected Path makePath(ResourceLocation location) {
		return resolveData(location).resolve("tags").resolve(getTagFolder()).resolve(location.getPath() + ".json");
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags";
	}
}
