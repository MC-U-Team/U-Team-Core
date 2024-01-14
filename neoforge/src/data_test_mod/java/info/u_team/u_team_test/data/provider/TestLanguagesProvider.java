package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;
import info.u_team.u_team_test.init.TestCreativeTabs;
import info.u_team.u_team_test.init.TestItems;

public class TestLanguagesProvider extends CommonLanguagesProvider {
	
	public TestLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		add(TestCreativeTabs.TAB.get(), "UTeamTest Tab");
		
		addItem(TestItems.BASIC_ARMOR.helmet(), "Basic Helmet");
		addItem(TestItems.BASIC_ARMOR.chestplate(), "Basic Chestplate");
		addItem(TestItems.BASIC_ARMOR.leggings(), "Basic Leggings");
		addItem(TestItems.BASIC_ARMOR.boots(), "Basic Boots");
		
		addItem(TestItems.BASIC_TOOL.axe(), "Basic Axe");
		addItem(TestItems.BASIC_TOOL.hoe(), "Basic Hoe");
		addItem(TestItems.BASIC_TOOL.pickaxe(), "Basic Pickaxe");
		addItem(TestItems.BASIC_TOOL.shovel(), "Basic Shovel");
		addItem(TestItems.BASIC_TOOL.sword(), "Basic Sword");
		
		addBlock(TestBlocks.BASIC, "Basic Block");
		addBlock(TestBlocks.BASIC_ENERGY_CREATOR, "Energy Creator");
		addBlock(TestBlocks.BASIC_FLUID_INVENTORY, "Fluid Inventory");
		
		addBlock(TestBlocks.TEST_FLUID, "Test Fluid");
		addItem(TestItems.TEST_FLUID_BUCKET, "Test Fluid Bucket");
		
		// German test for umlaut
		addBlock("de_de", TestBlocks.BASIC, "Ä");
		addBlock("de_de", TestBlocks.BASIC_ENERGY_CREATOR, "Üabc");
		addBlock("de_de", TestBlocks.BASIC_FLUID_INVENTORY, "Großes");
	}
	
}
