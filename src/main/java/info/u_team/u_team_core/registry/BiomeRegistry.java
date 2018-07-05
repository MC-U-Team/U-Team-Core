package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.api.registry.IUBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BiomeRegistry {
	
	static List<Biome> biomes = new ArrayList<>();
	
	public static void register(String modid, Biome biome) {
		if (biome instanceof IUBiome) {
			IUBiome iubiome = (IUBiome) biome;
			biome.setRegistryName(modid, iubiome.getName());
		}
		biomes.add(biome);
	}
	
	public static void register(String modid, Collection<Biome> list) {
		list.forEach(block -> register(modid, block));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Biome> event) {
		IForgeRegistry<Biome> registry = event.getRegistry();
		biomes.forEach(registry::register);
	}
	
}
