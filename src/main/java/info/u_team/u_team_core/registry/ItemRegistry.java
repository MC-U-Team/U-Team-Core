package info.u_team.u_team_core.registry;

import java.util.*;

import info.u_team.u_team_core.api.IUItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemRegistry {
	
	static List<Item> items = new ArrayList<>();
	
	public static void register(String modid, Item item) {
		if (item instanceof IUItem) {
			IUItem iuitem = (IUItem) item;
			item.setRegistryName(modid, iuitem.getName());
			item.setUnlocalizedName(modid + ":" + iuitem.getName());
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
