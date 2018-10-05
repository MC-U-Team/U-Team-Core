package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.api.registry.IUSoundEvent;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Sound API<br>
 * -> Registry
 * 
 * @date 05.10.2018
 * @author HyCraftHD
 */
public class SoundRegistry {
	
	static List<SoundEvent> sounds = new ArrayList<>();
	
	public static void register(String modid, SoundEvent sound) {
		if (sound instanceof IUSoundEvent) {
			IUSoundEvent iuitem = (IUSoundEvent) sound;
			sound.setRegistryName(modid, iuitem.getName());
		}
		sounds.add(sound);
	}
	
	public static void register(String modid, Collection<SoundEvent> list) {
		list.forEach(sound -> register(modid, sound));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<SoundEvent> event) {
		IForgeRegistry<SoundEvent> registry = event.getRegistry();
		sounds.forEach(registry::register);
	}
	
}
