package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class PotionTypeRegistry {
	
	static List<PotionType> potiontypes = new ArrayList<>();
	
	public static void register(String modid, PotionType potiontype) {
		BaseRegistry.register(modid, potiontype, potiontypes);
	}
	
	public static void register(String modid, Collection<PotionType> list) {
		list.forEach(potiontype -> register(modid, potiontype));
	}
	
	public static void register(String modid, Class<?> clazz) {
		register(modid, RegistryUtil.getRegistryEntries(PotionType.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<PotionType> event) {
		IForgeRegistry<PotionType> registry = event.getRegistry();
		potiontypes.forEach(registry::register);
	}
}
