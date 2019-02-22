package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUPotion;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class VillagerProfessionRegistry {
	
	static List<VillagerProfession> villagerprofessions = new ArrayList<>();
	
	public static void register(String modid, VillagerProfession villagerprofession) {
		if (villagerprofession instanceof IUPotion) {
			IUPotion iuvillagerprofession = (IUPotion) villagerprofession;
			villagerprofession.setRegistryName(modid, iuvillagerprofession.getEntryName());
		}
		villagerprofessions.add(villagerprofession);
	}
	
	public static void register(String modid, Collection<VillagerProfession> list) {
		list.forEach(villagerprofession -> register(modid, villagerprofession));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(VillagerProfession.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<VillagerProfession> event) {
		IForgeRegistry<VillagerProfession> registry = event.getRegistry();
		villagerprofessions.forEach(registry::register);
	}
}
