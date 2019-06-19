package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.item.tool.UToolMaterial;
import net.minecraft.item.crafting.Ingredient;

public class TestToolMaterial {
	
	public static final IToolMaterial BASIC = new UToolMaterial(new int[] { 8, 0, 4, 2, 6 }, new float[] { -3.1F, -1F, -2F, -2F, 0F }, 2, 500, 5F, 8, 30, () -> Ingredient.fromItems(TestItems.BASIC));
}
