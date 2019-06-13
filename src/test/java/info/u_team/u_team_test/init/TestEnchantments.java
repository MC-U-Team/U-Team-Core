package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.EnchantmentRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.enchantment.EnchantmentAutoSmelt;
import net.minecraft.enchantment.Enchantment;

public class TestEnchantments {
	
	public static final Enchantment auto_smelt = new EnchantmentAutoSmelt("auto_smelt");
	
	public static void construct() {
		EnchantmentRegistry.register(TestMod.modid, TestEnchantments.class);
	}
	
}
