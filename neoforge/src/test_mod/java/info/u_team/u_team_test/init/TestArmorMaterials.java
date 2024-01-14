package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.armor.UArmorMaterialVanilla;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class TestArmorMaterials {
	
	public static final ArmorMaterial BASIC = new UArmorMaterialVanilla(20, new int[] { 5, 6, 8, 2 }, 20, () -> SoundEvents.PARROT_IMITATE_GUARDIAN, 1, 1, () -> Ingredient.of(Items.ZOMBIE_HEAD));
	
}
