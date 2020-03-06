package info.u_team.u_team_core.data;

import java.nio.file.Path;

import net.minecraft.block.Block;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	public CommonBlockTagsProvider(GenerationData data) {
		super(data, ForgeRegistries.BLOCKS);
	}
	
	@Override
	protected Path makePath(ResourceLocation location) {
		return resolveData(location).resolve("tags").resolve("blocks").resolve(location.getPath() + ".json");
	}
	
	@Override
	protected void setCollection(TagCollection<Block> collection) {
		BlockTags.setCollection(collection);
	}
	
	@Override
	public String getName() {
		return "Block-Tags";
	}
}
