package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_test.init.TestBlocks;

public class TestBlockStatesProvider extends CommonBlockStatesProvider {
	
	public TestBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerStatesAndModels() {
		simpleBlock(TestBlocks.BASIC.get());
		facingBlock(TestBlocks.BASIC_TILEENTITY, cubeFacing(TestBlocks.BASIC_TILEENTITY.getRegistryName().getPath(), modLoc("block/tileentity_front"), modLoc("block/tileentity")));
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR);
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY);
	}
}
