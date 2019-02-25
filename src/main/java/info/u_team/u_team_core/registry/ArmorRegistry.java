package info.u_team.u_team_core.registry;

import java.util.Collection;
import java.util.stream.*;

import info.u_team.u_team_core.item.armor.Armor;
import info.u_team.u_team_core.util.RegistryUtil;

public class ArmorRegistry {
	
	public static void register(String modid, Armor armor) {
		ItemRegistry.register(modid, Stream.of(armor.getItemArray()).collect(Collectors.toList()));
	}
	
	public static void register(String modid, Collection<Armor> list) {
		list.forEach(item -> register(modid, item));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getClassEntries(Armor.class, clazz));
	}
	
}
