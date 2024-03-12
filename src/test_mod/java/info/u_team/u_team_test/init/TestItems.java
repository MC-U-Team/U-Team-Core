package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.USpawnEggItem;
import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.item.armor.ArmorSetCreator;
import info.u_team.u_team_core.item.tool.ToolSet;
import info.u_team.u_team_core.item.tool.ToolSetCreator;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.BasicFoodItem;
import info.u_team.u_team_test.item.BasicItem;
import info.u_team.u_team_test.item.BetterEnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestItems {
	
	public static final CommonDeferredRegister<Item> ITEMS = CommonDeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MODID);
	
	public static final RegistryObject<BasicItem> BASIC = ITEMS.register("basicitem", BasicItem::new);
	
	public static final RegistryObject<BetterEnderPearlItem> BETTER_ENDERPEARL = ITEMS.register("better_enderpearl", BetterEnderPearlItem::new);
	
	public static final RegistryObject<BasicFoodItem> BASIC_FOOD = ITEMS.register("basicfood", BasicFoodItem::new);
	
	public static final ToolSet BASIC_TOOL = ToolSetCreator.create(ITEMS, "basictool", TestItemGroups.GROUP, new Properties(), TestToolMaterial.BASIC);
	public static final ArmorSet BASIC_ARMOR = ArmorSetCreator.create(ITEMS, "basicarmor", TestItemGroups.GROUP, new Properties(), TestArmorMaterial.BASIC);
	
	public static final RegistryObject<USpawnEggItem> TEST_LIVING_SPAWN_EGG = ITEMS.register("test_living_spawn_egg", () -> new USpawnEggItem(TestItemGroups.GROUP, new Properties(), TestEntityTypes.TEST_LIVING, 0xFF0000, 0x00FF00));
	
	public static void registerMod(IEventBus bus) {
		ITEMS.register(bus);
	}
}
