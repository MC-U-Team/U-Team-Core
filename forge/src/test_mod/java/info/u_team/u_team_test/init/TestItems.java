package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.USpawnEggItem;
import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.item.armor.ArmorSetCreator;
import info.u_team.u_team_core.item.tier.TierSet;
import info.u_team.u_team_core.item.tier.TierSetCreator;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.BasicFoodItem;
import info.u_team.u_team_test.item.BasicItem;
import info.u_team.u_team_test.item.BetterEnderPearlItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TestItems {
	
	public static final CommonDeferredRegister<Item> ITEMS = CommonDeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MODID);
	
	public static final RegistryObject<BasicItem> BASIC = ITEMS.register("basic_item", BasicItem::new);
	public static final RegistryObject<BasicFoodItem> BASIC_FOOD = ITEMS.register("basic_food", BasicFoodItem::new);
	public static final RegistryObject<BetterEnderPearlItem> BETTER_ENDERPEARL = ITEMS.register("better_enderpearl", BetterEnderPearlItem::new);
	
	public static final TierSet BASIC_TOOL = TierSetCreator.create(ITEMS, "basic_tool", new Properties().rarity(Rarity.UNCOMMON), TestTiers.BASIC);
	public static final ArmorSet BASIC_ARMOR = ArmorSetCreator.create(ITEMS, "basic_armor", new Properties().rarity(Rarity.RARE).fireResistant(), TestArmorMaterials.BASIC);
	
	public static final RegistryObject<USpawnEggItem> TEST_LIVING_SPAWN_EGG = ITEMS.register("test_living_spawn_egg", () -> new USpawnEggItem(new Properties(), TestEntityTypes.TEST_LIVING, 0xFF0000, 0x00FF00));
	
	public static void registerMod(IEventBus bus) {
		ITEMS.register(bus);
	}
}
