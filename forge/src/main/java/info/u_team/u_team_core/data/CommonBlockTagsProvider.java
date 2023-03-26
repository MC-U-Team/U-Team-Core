package info.u_team.u_team_core.data;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	@SuppressWarnings("deprecation")
	public CommonBlockTagsProvider(GenerationData generationData) {
		super(generationData, ForgeRegistries.Keys.BLOCKS, block -> block.builtInRegistryHolder().key());
	}
	
	@Override
	public String getName() {
		return "Block-Tags";
	}
	
}
