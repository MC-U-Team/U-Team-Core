package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonBlockStatesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;

public class TestBlockStatesProvider extends CommonBlockStatesProvider {
	
	public TestBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerStatesAndModels() {
		simpleBlock(TestBlocks.BASIC.get());
		facingBlock(TestBlocks.BASIC_BLOCKENTITY.get(), cubeFacing(TestBlocks.BASIC_BLOCKENTITY.get().getRegistryName().getPath(), modLoc("block/basic_block_entity_front"), modLoc("block/basic_block_entity")));
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR.get());
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY.get());
	}
}
