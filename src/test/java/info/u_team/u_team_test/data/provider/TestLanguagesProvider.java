package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_test.init.*;

public class TestLanguagesProvider extends CommonLanguagesProvider {
	
	public TestLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void addTranslations() {
		add(TestItemGroups.GROUP, "UTeamTest Tab");
		
		add(TestItems.BASIC.get(), "Basic Item");
		add(TestItems.BASIC.get().getTranslationKey() + ".outofrange", "\u00A74Out of range");
		add(TestItems.BETTER_ENDERPEARL.get(), "Better Enderpearl");
		add(TestItems.BASIC_FOOD.get(), "Basic Food");
		
		add(TestItems.BASIC_ARMOR.getHelmet(), "Basic Helmet");
		add(TestItems.BASIC_ARMOR.getChestplate(), "Basic Chestplate");
		add(TestItems.BASIC_ARMOR.getLeggings(), "Basic Leggings");
		add(TestItems.BASIC_ARMOR.getBoots(), "Basic Boots");
		
		add(TestItems.BASIC_TOOL.getAxe(), "Basic Axe");
		add(TestItems.BASIC_TOOL.getHoe(), "Basic Hoe");
		add(TestItems.BASIC_TOOL.getPickaxe(), "Basic Pickaxe");
		add(TestItems.BASIC_TOOL.getShovel(), "Basic Shovel");
		add(TestItems.BASIC_TOOL.getSword(), "Basic Sword");
		
		add(TestBlocks.BASIC.get(), "Basic Block");
		add(TestBlocks.BASIC_TILEENTITY.get(), "Tile Entity Block");
		add(TestBlocks.BASIC_ENERGY_CREATOR.get(), "Energy Creator");
		add(TestBlocks.BASIC_FLUID_INVENTORY.get(), "Fluid Inventory");
		
		add(TestEntityTypes.BETTER_ENDERPEARL.get(), "Better Enderpearl");
		
		add(TestEnchantments.AUTO_SMELT.get(), "Auto Smelt");
		
		add(TestEffects.RADIATION.get(), "Radiation");
		
		add("death.attack.radiation", "%1$s dies of radiation");
		
		add("item.minecraft.potion.effect.radiation", "Potion of Radiation");
		add("item.minecraft.splash_potion.effect.radiation", "Splash Potion of Radiation");
		add("item.minecraft.lingering_potion.effect.radiation", "Lingering Potion of Radiation");
		
		add(TestBiomes.BASIC.get(), "Basic");
	}
	
}
