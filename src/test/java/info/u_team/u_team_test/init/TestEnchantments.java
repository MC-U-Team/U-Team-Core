package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.enchantment.EnchantmentAutoSmelt;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.modid, bus = Bus.MOD)
public class TestEnchantments {
	
	public static final Enchantment auto_smelt = new EnchantmentAutoSmelt("auto_smelt");
	
	@SubscribeEvent
	public static void register(Register<Enchantment> event) {
		BaseRegistryUtil.getAllRegistryEntriesAndApplyNames(TestMod.modid, Enchantment.class).forEach(event.getRegistry()::register);
	}
	
}
