package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IURegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class EntityTypeRegistry {
	
	static List<EntityType<?>> tileentitytypes = new ArrayList<>();
	
	public static void register(String modid, EntityType<?> entitytype) {
		if (entitytype instanceof IURegistry) {
			IURegistry iuentitytype = (IURegistry) entitytype;
			entitytype.setRegistryName(modid, iuentitytype.getEntryName());
		}
		tileentitytypes.add(entitytype);
	}
	
	public static void register(String modid, Collection<EntityType<?>> list) {
		list.forEach(entitytype -> register(modid, entitytype));
	}
	
	@SuppressWarnings("unchecked")
	public static void register(String modid, Class<?> clazz) {
		register(modid, (Collection<EntityType<?>>) RegistryUtil.getRegistryEntries(EntityType.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<EntityType<?>> event) {
		IForgeRegistry<EntityType<?>> registry = event.getRegistry();
		tileentitytypes.forEach(registry::register);
	}
}
