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
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR.get());
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY.get());
		
		simpleBlock(TestBlocks.TEST_FLUID.get(), models().getBuilder(getPath(TestBlocks.TEST_FLUID.get())).texture("particle", mcLoc("block/water_still")));
	}
}
