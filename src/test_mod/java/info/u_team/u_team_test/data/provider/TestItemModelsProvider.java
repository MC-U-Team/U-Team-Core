package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_test.init.*;

public class TestItemModelsProvider extends CommonItemModelsProvider {
	
	public TestItemModelsProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerModels() {
		// Items
		simpleGenerated(TestItems.BASIC.get());
		simpleGenerated(TestItems.BASIC_FOOD.get());
		simpleGenerated(TestItems.BETTER_ENDERPEARL.get());
		iterateItems(TestItems.BASIC_TOOL, this::simpleHandheld);
		iterateItems(TestItems.BASIC_ARMOR, this::simpleGenerated);
		// Blocks
		simpleBlock(TestBlocks.BASIC.get());
		simpleBlock(TestBlocks.BASIC_TILEENTITY.get());
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR.get());
		simpleBlock(TestBlocks.BASIC_FLUID_INVENTORY.get());
	}
	
}
