package info.u_team.u_team_core.data;

import java.nio.file.Path;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	protected CommonBlockTagsProvider(String name, DataGenerator generator) {
		super(name, generator, ForgeRegistries.BLOCKS);
	}
	
	@Override
	protected Path makePath(ResourceLocation id) {
		return path.resolve(id.getNamespace()).resolve("tags").resolve("blocks").resolve(id.getPath() + ".json");
	}
	
	@Override
	protected void setCollection(TagCollection<Block> collection) {
		BlockTags.setCollection(collection);
	}
}
