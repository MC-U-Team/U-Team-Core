package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IURegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid, bus = Bus.MOD)
public class TileEntityTypeRegistry {
	
	static List<TileEntityType<?>> tileentitytypes = new ArrayList<>();
	
	public static void register(String modid, TileEntityType<?> tileentitytype) {
		if (tileentitytype instanceof IURegistry) {
			IURegistry iutileentitytype = (IURegistry) tileentitytype;
			tileentitytype.setRegistryName(modid, iutileentitytype.getEntryName());
		}
		tileentitytypes.add(tileentitytype);
	}
	
	public static void register(String modid, Collection<TileEntityType<?>> list) {
		list.forEach(tileentitytype -> register(modid, tileentitytype));
	}
	
	@SuppressWarnings("unchecked")
	public static void register(String modid, Class<?> clazz) {
		register(modid, (Collection<TileEntityType<?>>) RegistryUtil.getRegistryEntries(TileEntityType.class, clazz));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<TileEntityType<?>> event) {
		IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
		tileentitytypes.forEach(registry::register);
	}
}
