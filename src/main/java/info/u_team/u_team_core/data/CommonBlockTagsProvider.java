package info.u_team.u_team_core.data;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	@SuppressWarnings("deprecation")
	public CommonBlockTagsProvider(GenerationData generationData) {
		super(generationData, Registry.BLOCK);
	}
	
	@Override
	protected TagBuilder getOrCreateRawBuilder(TagKey<Block> tag) {
		return super.getOrCreateRawBuilder(tag);
	}
	
}
