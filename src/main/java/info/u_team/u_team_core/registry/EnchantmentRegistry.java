package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUPotion;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class EnchantmentRegistry {
	
	static List<Enchantment> enchantments = new ArrayList<>();
	
	public static void register(String modid, Enchantment enchantment) {
		if (enchantment instanceof IUPotion) {
			IUPotion iuenchantment = (IUPotion) enchantment;
			enchantment.setRegistryName(modid, iuenchantment.getEntryName());
		}
		enchantments.add(enchantment);
	}
	
	public static void register(String modid, Collection<Enchantment> list) {
		list.forEach(enchantment -> register(modid, enchantment));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(Enchantment.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> registry = event.getRegistry();
		enchantments.forEach(registry::register);
	}
}
