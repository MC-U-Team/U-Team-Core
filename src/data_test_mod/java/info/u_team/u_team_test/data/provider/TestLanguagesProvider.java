package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonLanguagesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestBlocks;
import info.u_team.u_team_test.init.TestCreativeTabs;
import info.u_team.u_team_test.init.TestEnchantments;
import info.u_team.u_team_test.init.TestEntityTypes;
import info.u_team.u_team_test.init.TestItems;
import info.u_team.u_team_test.init.TestMobEffects;

public class TestLanguagesProvider extends CommonLanguagesProvider {
	
	public TestLanguagesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		add(TestCreativeTabs.TAB, "UTeamTest Tab");
		
		addItem(TestItems.BASIC, "Basic Item");
		add(TestItems.BASIC.get().getDescriptionId() + ".outofrange", "\u00A74Out of range");
		addItem(TestItems.BETTER_ENDERPEARL, "Better Enderpearl");
		addItem(TestItems.BASIC_FOOD, "Basic Food");
		
		addItem(TestItems.BASIC_ARMOR.helmet(), "Basic Helmet");
		addItem(TestItems.BASIC_ARMOR.chestplate(), "Basic Chestplate");
		addItem(TestItems.BASIC_ARMOR.leggings(), "Basic Leggings");
		addItem(TestItems.BASIC_ARMOR.boots(), "Basic Boots");
		
		addItem(TestItems.BASIC_TOOL.axe(), "Basic Axe");
		addItem(TestItems.BASIC_TOOL.hoe(), "Basic Hoe");
		addItem(TestItems.BASIC_TOOL.pickaxe(), "Basic Pickaxe");
		addItem(TestItems.BASIC_TOOL.shovel(), "Basic Shovel");
		addItem(TestItems.BASIC_TOOL.sword(), "Basic Sword");
		
		addItem(TestItems.TEST_LIVING_SPAWN_EGG, "Test Living Entity Spawn Egg");
		
		addBlock(TestBlocks.BASIC, "Basic Block");
		addBlock(TestBlocks.BASIC_BLOCKENTITY, "Block Entity Block");
		addBlock(TestBlocks.BASIC_ENERGY_CREATOR, "Energy Creator");
		addBlock(TestBlocks.BASIC_FLUID_INVENTORY, "Fluid Inventory");
		addBlock(TestBlocks.BASIC_SYNC, "Sync Block");
		addBlock(TestBlocks.BASIC_NO_ITEM, "No Item Block");
		addBlock(TestBlocks.BASIC_NO_ITEM_IMPLICIT, "No Item Implicit Block");
		
		addEntityType(TestEntityTypes.BETTER_ENDERPEARL, "Better Enderpearl");
		addEntityType(TestEntityTypes.TEST_LIVING, "Test Living Entity");
		
		addEnchantment(TestEnchantments.AUTO_SMELT, "Auto Smelt");
		
		addEffect(TestMobEffects.RADIATION, "Radiation");
		
		add("death.attack.radiation", "%1$s dies of radiation");
		
		add("item.minecraft.potion.effect.radiation", "Potion of Radiation");
		add("item.minecraft.splash_potion.effect.radiation", "Splash Potion of Radiation");
		add("item.minecraft.lingering_potion.effect.radiation", "Lingering Potion of Radiation");
		
		// German test for umlaut
		addBlock("de_de", TestBlocks.BASIC, "Ä");
		addBlock("de_de", TestBlocks.BASIC_BLOCKENTITY, "Ö");
		addBlock("de_de", TestBlocks.BASIC_ENERGY_CREATOR, "Üabc");
		addBlock("de_de", TestBlocks.BASIC_FLUID_INVENTORY, "Großes");
	}
	
}
