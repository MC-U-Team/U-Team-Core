package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IURegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class BiomeRegistry {
	
	static List<Biome> biomes = new ArrayList<>();
	
	public static void register(String modid, Biome biome) {
		if (biome instanceof IURegistry) {
			IURegistry iubiome = (IURegistry) biome;
			biome.setRegistryName(modid, iubiome.getEntryName());
		}
		biomes.add(biome);
	}
	
	public static void register(String modid, Collection<Biome> list) {
		list.forEach(biome -> register(modid, biome));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(Biome.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Biome> event) {
		IForgeRegistry<Biome> registry = event.getRegistry();
		biomes.forEach(registry::register);
	}
}
