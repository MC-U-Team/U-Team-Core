package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.api.registry.IUEntityEntry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class EntityRegistry {
	
	static List<EntityEntry> entities = new ArrayList<>();
	
	public static void register(String modid, IUEntityEntry entry) {
		entities.add(entry.getEntry());
	}
	
	public static void register(String modid, Collection<IUEntityEntry> list) {
		list.forEach(entity -> register(modid, entity));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<EntityEntry> event) {
		IForgeRegistry<EntityEntry> registry = event.getRegistry();
		entities.forEach(registry::register);
	}
	
}
