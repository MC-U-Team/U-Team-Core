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
		facingBlock(TestBlocks.BASIC_TILEENTITY.get(), cubeFacing(TestBlocks.BASIC_TILEENTITY.get().getRegistryName().getPath(), modLoc("block/tileentity_front"), modLoc("block/tileentity")));
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR.get());
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY.get());
	}
}
