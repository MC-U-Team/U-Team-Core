package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.enchantment.AutoSmeltEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class TestEnchantments {
	
	public static final CommonDeferredRegister<Enchantment> ENCHANTMENTS = CommonDeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TestMod.MODID);
	
	public static final RegistryObject<Enchantment> AUTO_SMELT = ENCHANTMENTS.register("auto_smelt", AutoSmeltEnchantment::new);
	
	public static void register(IEventBus bus) {
		ENCHANTMENTS.register(bus);
	}
	
}
