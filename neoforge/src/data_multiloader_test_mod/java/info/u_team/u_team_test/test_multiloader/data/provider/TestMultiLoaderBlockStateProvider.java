package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonBlockStateProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlocks;

public class TestMultiLoaderBlockStateProvider extends CommonBlockStateProvider {
	
	public TestMultiLoaderBlockStateProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		simpleBlock(TestMultiLoaderBlocks.TEST.get());
		simpleBlock(TestMultiLoaderBlocks.TEST_SYNC.get());
		facingBlock(TestMultiLoaderBlocks.TEST_INVENTORY.get(), cubeFacing(getPath(TestMultiLoaderBlocks.TEST_INVENTORY.get()), modLoc("block/test_inventory_block_front"), modLoc("block/test_inventory_block")));
		simpleBlock(TestMultiLoaderBlocks.TEST_NO_ITEM.get());
		simpleBlock(TestMultiLoaderBlocks.TEST_NO_ITEM_IMPLICIT.get());
	}
	
}
