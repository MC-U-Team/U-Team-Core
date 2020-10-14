package info.u_team.u_team_core.data;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public abstract class CommonBlockTagsProvider extends CommonTagsProvider<Block> {
	
	@SuppressWarnings("deprecation")
	public CommonBlockTagsProvider(GenerationData data) {
		super(data, Registry.BLOCK);
	}
	
	@Override
	protected String getTagFolder() {
		return "blocks";
	}
	
	@Override
	public String getName() {
		return "Block-Tags";
	}
}