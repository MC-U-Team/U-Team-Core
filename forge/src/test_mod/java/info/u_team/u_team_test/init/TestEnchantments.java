package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.enchantment.AutoSmeltEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class TestEnchantments {
	
	public static final CommonRegister<Enchantment> ENCHANTMENTS = CommonRegister.create(Registries.ENCHANTMENT, TestMod.MODID);
	
	public static final RegistryEntry<Enchantment> AUTO_SMELT = ENCHANTMENTS.register("auto_smelt", AutoSmeltEnchantment::new);
	
	public static void register() {
		ENCHANTMENTS.register();
	}
	
}
