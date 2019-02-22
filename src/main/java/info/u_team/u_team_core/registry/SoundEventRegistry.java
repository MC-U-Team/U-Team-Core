package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUPotion;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class SoundEventRegistry {
	
	static List<SoundEvent> soundevents = new ArrayList<>();
	
	public static void register(String modid, SoundEvent soundevent) {
		if (soundevent instanceof IUPotion) {
			IUPotion iusoundevent = (IUPotion) soundevent;
			soundevent.setRegistryName(modid, iusoundevent.getEntryName());
		}
		soundevents.add(soundevent);
	}
	
	public static void register(String modid, Collection<SoundEvent> list) {
		list.forEach(soundevent -> register(modid, soundevent));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(SoundEvent.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<SoundEvent> event) {
		IForgeRegistry<SoundEvent> registry = event.getRegistry();
		soundevents.forEach(registry::register);
	}
}
