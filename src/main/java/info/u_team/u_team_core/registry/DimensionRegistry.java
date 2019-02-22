package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUPotion;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class DimensionRegistry {
	
	static List<ModDimension> dimensions = new ArrayList<>();
	
	public static void register(String modid, ModDimension dimension) {
		if (dimension instanceof IUPotion) {
			IUPotion iudimension = (IUPotion) dimension;
			dimension.setRegistryName(modid, iudimension.getEntryName());
		}
		dimensions.add(dimension);
	}
	
	public static void register(String modid, Collection<ModDimension> list) {
		list.forEach(dimension -> register(modid, dimension));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(ModDimension.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<ModDimension> event) {
		IForgeRegistry<ModDimension> registry = event.getRegistry();
		dimensions.forEach(registry::register);
	}
}
