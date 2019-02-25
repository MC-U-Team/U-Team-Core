package info.u_team.u_team_core.registry;

import java.util.Collection;
import java.util.stream.*;

import info.u_team.u_team_core.item.armor.ArmorSet;
import info.u_team.u_team_core.util.RegistryUtil;

public class ArmorSetRegistry {
	
	public static void register(String modid, ArmorSet armorset) {
		ItemRegistry.register(modid, Stream.of(armorset.getItemArray()).filter(entry -> entry != null).collect(Collectors.toList()));
	}
	
	public static void register(String modid, Collection<ArmorSet> list) {
		list.forEach(item -> register(modid, item));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getClassEntries(ArmorSet.class, clazz));
	}
	
}
