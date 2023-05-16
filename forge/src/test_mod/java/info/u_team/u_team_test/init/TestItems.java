package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.item.USpawnEggItem;
import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.item.armor.ArmorSetCreator;
import info.u_team.u_team_core.item.tier.TierSet;
import info.u_team.u_team_core.item.tier.TierSetCreator;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.BasicFoodItem;
import info.u_team.u_team_test.item.BasicItem;
import info.u_team.u_team_test.item.BetterEnderPearlItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;

public class TestItems {
	
	public static final CommonRegister<Item> ITEMS = CommonRegister.create(Registries.ITEM, TestMod.MODID);
	
	public static final RegistryEntry<BasicItem> BASIC = ITEMS.register("basic_item", BasicItem::new);
	public static final RegistryEntry<BasicFoodItem> BASIC_FOOD = ITEMS.register("basic_food", BasicFoodItem::new);
	public static final RegistryEntry<BetterEnderPearlItem> BETTER_ENDERPEARL = ITEMS.register("better_enderpearl", BetterEnderPearlItem::new);
	
	public static final TierSet BASIC_TOOL = TierSetCreator.create(ITEMS, "basic_tool", new Properties().rarity(Rarity.UNCOMMON), TestTiers.BASIC);
	public static final ArmorSet BASIC_ARMOR = ArmorSetCreator.create(ITEMS, "basic_armor", new Properties().rarity(Rarity.RARE).fireResistant(), TestArmorMaterials.BASIC);
	
	public static final RegistryEntry<USpawnEggItem> TEST_LIVING_SPAWN_EGG = ITEMS.register("test_living_spawn_egg", () -> new USpawnEggItem(new Properties(), TestEntityTypes.TEST_LIVING, 0xFF0000, 0x00FF00));
	
	public static void register() {
		ITEMS.register();
	}
}
