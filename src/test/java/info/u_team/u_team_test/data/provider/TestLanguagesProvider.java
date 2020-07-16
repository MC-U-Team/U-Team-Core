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
		
		addItem(TestItems.BASIC, "Basic Item");
		add(TestItems.BASIC.get().getTranslationKey() + ".outofrange", "\u00A74Out of range");
		addItem(TestItems.BETTER_ENDERPEARL, "Better Enderpearl");
		addItem(TestItems.BASIC_FOOD, "Basic Food");
		
		addItem(TestItems.BASIC_ARMOR.getHelmet(), "Basic Helmet");
		addItem(TestItems.BASIC_ARMOR.getChestplate(), "Basic Chestplate");
		addItem(TestItems.BASIC_ARMOR.getLeggings(), "Basic Leggings");
		addItem(TestItems.BASIC_ARMOR.getBoots(), "Basic Boots");
		
		addItem(TestItems.BASIC_TOOL.getAxe(), "Basic Axe");
		addItem(TestItems.BASIC_TOOL.getHoe(), "Basic Hoe");
		addItem(TestItems.BASIC_TOOL.getPickaxe(), "Basic Pickaxe");
		addItem(TestItems.BASIC_TOOL.getShovel(), "Basic Shovel");
		addItem(TestItems.BASIC_TOOL.getSword(), "Basic Sword");
		
		addBlock(TestBlocks.BASIC, "Basic Block");
		addBlock(TestBlocks.BASIC_TILEENTITY, "Tile Entity Block");
		addBlock(TestBlocks.BASIC_ENERGY_CREATOR, "Energy Creator");
		addBlock(TestBlocks.BASIC_FLUID_INVENTORY, "Fluid Inventory");
		
		addEntityType(TestEntityTypes.BETTER_ENDERPEARL, "Better Enderpearl");
		
		addEnchantment(TestEnchantments.AUTO_SMELT, "Auto Smelt");
		
		addEffect(TestEffects.RADIATION, "Radiation");
		
		add("death.attack.radiation", "%1$s dies of radiation");
		
		add("item.minecraft.potion.effect.radiation", "Potion of Radiation");
		add("item.minecraft.splash_potion.effect.radiation", "Splash Potion of Radiation");
		add("item.minecraft.lingering_potion.effect.radiation", "Lingering Potion of Radiation");
		
		addBiome(TestBiomes.BASIC, "Basic");
		
		// German test for umlaut
		addBlock("de_de", TestBlocks.BASIC, "Ä");
		addBlock("de_de", TestBlocks.BASIC_TILEENTITY, "Ö");
		addBlock("de_de", TestBlocks.BASIC_ENERGY_CREATOR, "Üabc");
		addBlock("de_de", TestBlocks.BASIC_FLUID_INVENTORY, "Großes");
	}
	
}
