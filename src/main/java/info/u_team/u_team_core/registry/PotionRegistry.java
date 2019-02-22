package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUPotion;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class PotionRegistry {
	
	static List<Potion> potions = new ArrayList<>();
	
	public static void register(String modid, Potion potion) {
		if (potion instanceof IUPotion) {
			IUPotion iupotion = (IUPotion) potion;
			potion.setRegistryName(modid, iupotion.getEntryName());
		}
		potions.add(potion);
	}
	
	public static void register(String modid, Collection<Potion> list) {
		list.forEach(potion -> register(modid, potion));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(Potion.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Potion> event) {
		IForgeRegistry<Potion> registry = event.getRegistry();
		potions.forEach(registry::register);
	}
}
