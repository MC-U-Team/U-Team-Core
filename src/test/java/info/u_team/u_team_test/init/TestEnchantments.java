package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.enchantment.AutoSmeltEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

public class TestEnchantments {
	
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, TestMod.MODID);
	
	public static final Enchantment AUTO_SMELT = new AutoSmeltEnchantment("auto_smelt");
	
	public static void register(IEventBus bus) {
		ENCHANTMENTS.register(bus);
	}
	
}
