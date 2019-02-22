package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.ItemBasic;

public class TestItems {
	
	public static ItemBasic basic = new ItemBasic("basicitem");
	
	public static void construct() {
		ItemRegistry.register(TestMod.modid, TestItems.class);
	}
	
}
