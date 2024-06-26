package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.item.SpawnEggCreator;
import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.item.armor.ArmorSetCreator;
import info.u_team.u_team_core.item.tier.TierSet;
import info.u_team.u_team_core.item.tier.TierSetCreator;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.item.TestDimensionTeleportItem;
import info.u_team.u_team_test.test_multiloader.item.TestEnderPearlItem;
import info.u_team.u_team_test.test_multiloader.item.TestFoodItem;
import info.u_team.u_team_test.test_multiloader.item.TestUseItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;

public class TestMultiLoaderItems {
	
	public static final CommonRegister<Item> ITEMS = CommonRegister.create(Registries.ITEM, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<Item> TEST = ITEMS.register("test_item", () -> new Item(new Properties()));
	
	public static final RegistryEntry<TestUseItem> TEST_USE = ITEMS.register("test_use_item", TestUseItem::new);
	public static final RegistryEntry<TestFoodItem> TEST_FOOD = ITEMS.register("test_food_item", TestFoodItem::new);
	public static final RegistryEntry<TestDimensionTeleportItem> TEST_DIMENSION_TELEPORT = ITEMS.register("test_dimension_teleport_item", TestDimensionTeleportItem::new);
	
	public static final RegistryEntry<TestEnderPearlItem> TEST_ENDERPEARL = ITEMS.register("test_enderpearl_item", TestEnderPearlItem::new);
	
	public static final RegistryEntry<SpawnEggItem> TEST_LIVING_SPAWN_EGG = ITEMS.register("test_living_spawn_egg", () -> SpawnEggCreator.create(new Properties(), TestMultiLoaderEntityTypes.TEST_LIVING, 0xFF0000, 0x00FF00));
	
	public static final TierSet TEST_TIER = TierSetCreator.create(ITEMS, "test", new Properties().fireResistant(), TestMultiLoaderTiers.TEST);
	
	public static final ArmorSet TEST_ARMOR = ArmorSetCreator.create(ITEMS, "test", new Properties().fireResistant(), () -> TestMultiLoaderArmorMaterials.TEST.getHolder().get());
	
	static void register() {
		ITEMS.register();
	}
	
}
