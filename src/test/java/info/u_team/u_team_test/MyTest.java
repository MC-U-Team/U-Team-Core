package info.u_team.u_team_test;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.item.tool.*;
import info.u_team.u_team_test.init.TestItems;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.crafting.Ingredient;

public class MyTest {
	
	public static Item test = new Item(new Properties());
	public static Item test2 = new Item(new Properties());
	public static Item test3 = new Item(new Properties());
	
	public static final IToolMaterial basic = new UToolMaterial(new int[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1F, -2F, -2F, 0F }, 2, 500, 5F, 8, 30, () -> Ingredient.fromItems(TestItems.basic));
	
	public static ToolSet set = ToolSetCreator.create("test", new Properties(),basic );
	
}
