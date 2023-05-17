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
		
		addItem(TestMultiLoaderItems.ARMOR.helmet(), "Test Helmet");
		addItem(TestMultiLoaderItems.ARMOR.chestplate(), "Test Chestplate");
		addItem(TestMultiLoaderItems.ARMOR.leggings(), "Test Leggings");
		addItem(TestMultiLoaderItems.ARMOR.boots(), "Test Boots");
		
		addItem(TestMultiLoaderItems.TIER.axe(), "Test Axe");
		addItem(TestMultiLoaderItems.TIER.hoe(), "Test Hoe");
		addItem(TestMultiLoaderItems.TIER.pickaxe(), "Test Pickaxe");
		addItem(TestMultiLoaderItems.TIER.shovel(), "Test Shovel");
		addItem(TestMultiLoaderItems.TIER.sword(), "Test Sword");
		
		addBlock(TestMultiLoaderBlocks.TEST, "Test Block");
	}
	
}
