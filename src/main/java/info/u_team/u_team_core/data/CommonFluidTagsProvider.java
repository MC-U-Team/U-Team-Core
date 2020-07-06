package info.u_team.u_team_core.data;

import java.nio.file.Path;

import net.minecraft.fluid.Fluid;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonFluidTagsProvider extends CommonTagsProvider<Fluid> {
	
	public CommonFluidTagsProvider(GenerationData data) {
		super(data, Registry.FLUID);
	}
	
	@Override
	protected Path makePath(ResourceLocation location) {
		return resolveData(location).resolve("tags").resolve("fluids").resolve(location.getPath() + ".json");
	}
	
	@Override
	public String getName() {
		return "Fluid-Tags";
	}
}
