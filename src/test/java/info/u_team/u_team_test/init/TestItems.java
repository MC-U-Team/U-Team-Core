package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.ItemBasic;
import net.minecraft.item.Item;

public class TestItems {
	
	public static ItemBasic basic = new ItemBasic("basicitem");
	
	public static void construct() {
		ItemRegistry.register(TestMod.modid, RegistryUtil.getRegistryEntries(Item.class, TestItems.class));
	}
	
}
