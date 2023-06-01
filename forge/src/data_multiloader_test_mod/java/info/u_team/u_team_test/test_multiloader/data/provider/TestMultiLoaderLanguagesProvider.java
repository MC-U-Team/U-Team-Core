package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlocks;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderCreativeTabs;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderMobEffects;

public class TestMultiLoaderLanguagesProvider extends CommonLanguagesProvider {
	
	public TestMultiLoaderLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		add(TestMultiLoaderCreativeTabs.TEST.get(), "Multiloader Test Tab");
		
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
		addBlock(TestMultiLoaderBlocks.TEST_SYNC, "Test Sync Block");
		addBlock(TestMultiLoaderBlocks.TEST_NO_ITEM, "No Item Block");
		addBlock(TestMultiLoaderBlocks.TEST_NO_ITEM_IMPLICIT, "No Item Implicit Block");
		
		addEffect(TestMultiLoaderMobEffects.TEST, "Test");
		
		add("death.attack.test", "%1$s dies of test effect");
		
		add("item.minecraft.potion.effect.test", "Potion of Test Effect");
		add("item.minecraft.splash_potion.effect.test", "Splash Potion of Test Effect");
		add("item.minecraft.lingering_potion.effect.test", "Lingering Potion of Test Effect");
		add("item.minecraft.tipped_arrow.effect.test", "Arrow of Test Effect");
	}
	
}
