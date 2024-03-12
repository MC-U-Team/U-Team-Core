package info.u_team.u_team_test.data.provider;

import static info.u_team.u_team_test.init.TestTags.Blocks.TEST_TAG_1;
import static info.u_team.u_team_test.init.TestTags.Blocks.TEST_TAG_2;

import info.u_team.u_team_core.data.CommonBlockTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;

public class TestBlockTagsProvider extends CommonBlockTagsProvider {
	
	public TestBlockTagsProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerTags() {
		getBuilder(TEST_TAG_1).add(Blocks.ACACIA_BUTTON, Blocks.ACACIA_DOOR);
		
		getBuilder(TEST_TAG_2).add(TEST_TAG_1).add(Blocks.ACACIA_LOG);
		
		getBuilder(TEST_TAG_1).add(Blocks.BIRCH_LEAVES);
		getBuilder(TEST_TAG_1).add(BlockTags.BEDS);
		
		getBuilder(TEST_TAG_2).add(Blocks.ACACIA_LOG);
		getBuilder(TEST_TAG_2).add(TEST_TAG_1);
	}
	
}
