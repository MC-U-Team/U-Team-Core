package info.u_team.u_team_core.data;

import java.nio.file.Path;

import net.minecraft.fluid.Fluid;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonFluidTagsProvider extends CommonTagsProvider<Fluid> {
	
	public CommonFluidTagsProvider(GenerationData data) {
		super(data, ForgeRegistries.FLUIDS);
	}
	
	@Override
	protected Path makePath(ResourceLocation location) {
		return resolveData(location).resolve("tags").resolve("fluids").resolve(location.getPath() + ".json");
	}
	
	@Override
	protected void setCollection(TagCollection<Fluid> collection) {
		FluidTags.setCollection(collection);
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags";
	}
}
