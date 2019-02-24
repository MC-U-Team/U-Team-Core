package info.u_team.u_team_test.init;

import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.*;
import net.minecraft.item.Item;

public class TestItems {
	
	public static Item basic = new ItemBasic("basicitem");
	
	public static Item better_enderpearl = new ItemBetterEnderPearl("better_enderpearl");
	
	public static Item basicfood = new ItemBasicFood("basicfood");
	
	public static void construct() {
		ItemRegistry.register(TestMod.modid, TestItems.class);
	}
	
}
