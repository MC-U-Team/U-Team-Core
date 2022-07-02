package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonItemModelProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;
import info.u_team.u_team_test.init.TestItems;

public class TestItemModelProvider extends CommonItemModelProvider {
	
	public TestItemModelProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		// Items
		simpleGenerated(TestItems.BASIC.get());
		simpleGenerated(TestItems.BASIC_FOOD.get());
		simpleGenerated(TestItems.BETTER_ENDERPEARL.get());
		iterateItems(TestItems.BASIC_TOOL, this::simpleHandheld);
		iterateItems(TestItems.BASIC_ARMOR, this::simpleGenerated);
		spawnEgg(TestItems.TEST_LIVING_SPAWN_EGG.get());
		// Blocks
		simpleBlock(TestBlocks.BASIC.get());
		simpleBlock(TestBlocks.BASIC_BLOCKENTITY.get());
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR.get());
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY.get());
	}
	
}
