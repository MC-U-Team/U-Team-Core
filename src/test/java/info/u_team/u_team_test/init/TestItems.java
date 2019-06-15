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

@EventBusSubscriber(modid = TestMod.modid, bus = Bus.MOD)
public class TestItems {
	
	public static final Item basic = new BasicItem("basicitem");
	
	public static final Item better_enderpearl = new BetterEnderPearlItem("better_enderpearl");
	
	public static final Item basicfood = new BasicFoodItem("basicfood");
	
	public static final ToolSet basictool = ToolSetCreator.create("basictool", TestItemGroups.group, new Properties(), TestToolMaterial.basic);
	public static final ArmorSet basicarmor = ArmorSetCreator.create("basicarmor", TestItemGroups.group, new Properties(), TestArmorMaterial.basic);
	
	@SubscribeEvent
	public static void registerBlockItem(Register<Item> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.modid, Item.class).forEach(event.getRegistry()::register);
	}
	
}
