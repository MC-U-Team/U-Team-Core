package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.enchantment.TestEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class TestMultiLoaderEnchantments {
	
	public static final CommonRegister<Enchantment> ENCHANTMENTS = CommonRegister.create(Registries.ENCHANTMENT, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<Enchantment> TEST = ENCHANTMENTS.register("test", TestEnchantment::new);
	
	static void register() {
		ENCHANTMENTS.register();
	}
	
}
