package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.item.tool.*;
import info.u_team.u_team_core.registry.ToolSetRegistry;
import info.u_team.u_team_test.TestMod;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.crafting.Ingredient;

public class TestToolSets {
	
	public static final IToolMaterial basic = new UToolMaterial(new int[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1F, -2F, -2F, 0F }, 2, 500, 5F, 8, 30, () -> Ingredient.fromItems(TestItems.basic));
	
	public static final ToolSet basictool = ToolSetCreator.create("basictool", TestItemGroups.group, new Properties(), basic);
	
	public static void construct() {
		ToolSetRegistry.register(TestMod.modid, TestToolSets.class);
	}
	
}
