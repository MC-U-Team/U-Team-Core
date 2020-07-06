package info.u_team.u_team_core.data;

import java.nio.file.Path;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	@SuppressWarnings("deprecation")
	public CommonBlockTagsProvider(GenerationData data) {
		super(data, Registry.BLOCK);
	}
	
	@Override
	protected Path makePath(ResourceLocation location) {
		return resolveData(location).resolve("tags").resolve("blocks").resolve(location.getPath() + ".json");
	}
	
	@Override
	public String getName() {
		return "Block-Tags";
	}
}
