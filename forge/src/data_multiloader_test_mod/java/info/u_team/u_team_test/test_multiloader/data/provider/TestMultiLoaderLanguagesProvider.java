package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlocks;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;

public class TestMultiLoaderLanguagesProvider extends CommonLanguagesProvider {
	
	public TestMultiLoaderLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		addItem(TestMultiLoaderItems.TEST, "Test Item");
		
		addItem(TestMultiLoaderItems.ARMOR.helmet(), "Basic Helmet");
		addItem(TestMultiLoaderItems.ARMOR.chestplate(), "Basic Chestplate");
		addItem(TestMultiLoaderItems.ARMOR.leggings(), "Basic Leggings");
		addItem(TestMultiLoaderItems.ARMOR.boots(), "Basic Boots");
		
		addItem(TestMultiLoaderItems.TIER.axe(), "Basic Axe");
		addItem(TestMultiLoaderItems.TIER.hoe(), "Basic Hoe");
		addItem(TestMultiLoaderItems.TIER.pickaxe(), "Basic Pickaxe");
		addItem(TestMultiLoaderItems.TIER.shovel(), "Basic Shovel");
		addItem(TestMultiLoaderItems.TIER.sword(), "Basic Sword");
		
		addBlock(TestMultiLoaderBlocks.TEST, "Test Block");
	}
	
}
