package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.armor.*;
import info.u_team.u_team_core.item.tool.*;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class TestItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestMod.MODID);
	
	public static final Item BASIC = new BasicItem("basicitem");
	
	public static final Item BETTER_ENDERPEARL = new BetterEnderPearlItem("better_enderpearl");
	
	public static final Item BASIC_FOOD = new BasicFoodItem("basicfood");
	
	public static final ToolSet BASIC_TOOL = ToolSetCreator.create("basictool", TestItemGroups.GROUP, new Properties(), TestToolMaterial.BASIC);
	public static final ArmorSet BASIC_ARMOR = ArmorSetCreator.create("basicarmor", TestItemGroups.GROUP, new Properties(), TestArmorMaterial.BASIC);
	
	public static void register(IEventBus bus) {
		ITEMS.register(bus);
	}
}
