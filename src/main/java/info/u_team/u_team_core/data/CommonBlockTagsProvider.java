package info.u_team.u_team_core.data;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	public CommonBlockTagsProvider(GenerationData generationData) {
		super(generationData, ForgeRegistries.Keys.BLOCKS);
	}
	
	@Override
	public String getName() {
		return "Block-Tags";
	}
	
}
