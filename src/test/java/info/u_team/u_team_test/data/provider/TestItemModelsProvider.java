package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonItemModelsProvider;
import info.u_team.u_team_test.init.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class TestItemModelsProvider extends CommonItemModelsProvider {
	
	public TestItemModelsProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}
	
	@Override
	protected void registerModels() {
		// Items
		simpleGenerated(TestItems.BASIC);
		simpleGenerated(TestItems.BASIC_FOOD);
		simpleGenerated(TestItems.BETTER_ENDERPEARL);
		iterateItems(TestItems.BASIC_ARMOR, this::simpleGenerated);
		iterateItems(TestItems.BASIC_TOOL, this::simpleHandheld);
		// Blocks
		simpleBlock(TestBlocks.BASIC);
		simpleBlock(TestBlocks.BASIC_ENERGY_CREATOR);
		simpleBlock(TestBlocks.BASIC_TILEENTITY);
	}
	
}
