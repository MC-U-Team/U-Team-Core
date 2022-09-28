package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonBlockStateProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;

public class TestBlockStateProvider extends CommonBlockStateProvider {
	
	public TestBlockStateProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		simpleBlock(TestBlocks.BASIC.get());
		facingBlock(TestBlocks.BASIC_BLOCKENTITY.get(), cubeFacing(getPath(TestBlocks.BASIC_BLOCKENTITY.get()), modLoc("block/basic_block_entity_front"), modLoc("block/basic_block_entity")));
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR.get());
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY.get());
		simpleBlock(TestBlocks.BASIC_SYNC.get());
	}
}
