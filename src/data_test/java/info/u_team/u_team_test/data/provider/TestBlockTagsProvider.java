package info.u_team.u_team_test.data.provider;

import static info.u_team.u_team_test.init.TestTags.Blocks.*;
import info.u_team.u_team_core.data.*;
import net.minecraft.block.Blocks;
import net.minecraft.tags.*;


public class TestBlockTagsProvider extends CommonBlockTagsProvider {
	
	public TestBlockTagsProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerTags() {
		getBuilder(TEST_TAG_1).add(Blocks.ACACIA_BUTTON, Blocks.ACACIA_DOOR);
		getBuilder(TEST_TAG_2).addTag(TEST_TAG_1).add(Blocks.ACACIA_LOG);
		
		getBuilder(TEST_TAG_1).add(Blocks.BIRCH_LEAVES);
		getBuilder(TEST_TAG_1).addTag(BlockTags.BEDS);
	}
	
}
