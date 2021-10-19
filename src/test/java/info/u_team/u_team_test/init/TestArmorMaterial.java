package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.armor.UArmorMaterialVanilla;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class TestArmorMaterial {
	
	public static final ArmorMaterial BASIC = new UArmorMaterialVanilla(20, new int[] { 5, 6, 8, 2 }, 20, TestSoundEvents.BETTER_ENDERPEARL_USE, 1, 1, () -> Ingredient.of(TestItems.BASIC.get()));
	
}
