package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.ItemRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.item.*;
import net.minecraft.item.Item;

public class TestItems {
	
	public static final Item basic = new ItemBasic("basicitem");
	
	public static final Item better_enderpearl = new ItemBetterEnderPearl("better_enderpearl");
	
	public static final Item basicfood = new ItemBasicFood("basicfood");
	
	public static void construct() {
		ItemRegistry.register(TestMod.modid, TestItems.class);
	}
	
}
