package info.u_team.u_team_core.data;

import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

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
