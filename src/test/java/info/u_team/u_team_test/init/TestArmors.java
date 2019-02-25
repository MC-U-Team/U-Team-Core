package info.u_team.u_team_test.init;

import info.u_team.u_team_core.item.armor.*;
import info.u_team.u_team_core.registry.ArmorRegistry;
import info.u_team.u_team_test.TestMod;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.crafting.Ingredient;

public class TestArmors {
	
	public static final IArmorMaterial basic = new UArmorMaterialVanilla(20, new int[] { 5, 6, 8, 2 }, 20, TestSounds.better_enderpearl_use, 1, () -> Ingredient.fromItems(TestItems.basic));
	
	public static final Armor basicarmor = ArmorCreator.create("basicarmor", TestItemGroups.group, new Properties(), basic);
	
	public static void construct() {
		ArmorRegistry.register(TestMod.modid, TestArmors.class);
	}
}
