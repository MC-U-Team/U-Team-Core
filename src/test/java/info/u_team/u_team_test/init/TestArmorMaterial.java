package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.armor.UArmorMaterialVanilla;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;

public class TestArmorMaterial {
	
	public static final IArmorMaterial basic = new UArmorMaterialVanilla(20, new int[] { 5, 6, 8, 2 }, 20, TestSounds.better_enderpearl_use, 1, () -> Ingredient.fromItems(TestItems.basic));
	
}
