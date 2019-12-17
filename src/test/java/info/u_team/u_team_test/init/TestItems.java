package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.armor.*;
import info.u_team.u_team_core.item.tool.*;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD)
public class TestItems {
	
	public static final Item BASIC = new BasicItem("basicitem");
	
	public static final Item BETTER_ENDERPEARL = new BetterEnderPearlItem("better_enderpearl");
	
	public static final Item BASIC_FOOD = new BasicFoodItem("basicfood");
	
	public static final ToolSet BASIC_TOOL = ToolSetCreator.create("basictool", TestItemGroups.GROUP, new Properties(), TestToolMaterial.BASIC);
	public static final ArmorSet BASIC_ARMOR = ArmorSetCreator.create("basicarmor", TestItemGroups.GROUP, new Properties(), TestArmorMaterial.BASIC);
	
	@SubscribeEvent
	public static void registerBlockItem(Register<Item> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.MODID, Item.class).forEach(event.getRegistry()::register);
	}
	
}
