package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.registry.IUItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = UCoreMain.modid)
public class ItemRegistry {
	
	static List<Item> items = new ArrayList<>();
	
	public static void register(String modid, Item item) {
		if (item instanceof IUItem) {
			IUItem iuitem = (IUItem) item;
			item.setRegistryName(modid, iuitem.getName());
		}
		items.add(item);
		
	}
	
	public static void register(String modid, Collection<Item> list) {
		list.forEach(item -> register(modid, item));
	}
	
	@SubscribeEvent
	public static void event(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		items.forEach(registry::register);
	}
}
