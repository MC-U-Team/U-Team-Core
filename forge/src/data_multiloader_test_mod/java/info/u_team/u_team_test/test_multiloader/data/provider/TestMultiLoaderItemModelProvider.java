package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonItemModelProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlocks;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;

public class TestMultiLoaderItemModelProvider extends CommonItemModelProvider {
	
	public TestMultiLoaderItemModelProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		// Items
		simpleGenerated(TestMultiLoaderItems.TEST.get());
		simpleGenerated(TestMultiLoaderItems.TEST_USE.get());
		simpleGenerated(TestMultiLoaderItems.TEST_FOOD.get());
		simpleGenerated(TestMultiLoaderItems.TEST_ENDERPEARL.get());
		iterateItems(TestMultiLoaderItems.ARMOR, this::simpleHandheld);
		iterateItems(TestMultiLoaderItems.TIER, this::simpleGenerated);
		// Blocks
		simpleBlock(TestMultiLoaderBlocks.TEST.get());
		simpleBlock(TestMultiLoaderBlocks.TEST_SYNC.get());
	}
	
}
