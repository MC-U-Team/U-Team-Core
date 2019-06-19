package info.u_team.u_team_core.intern.init;

import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.api.IDyeableItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = UCoreMain.modid, value = Dist.CLIENT)
public class UCoreColors {
	
	private static final Set<Item> dyeableItems = new HashSet<>();
	
	public static <T extends Item & IDyeableItem> void addItem(T item) {
		dyeableItems.add(item);
	}
	
	@SubscribeEvent
	public static void register(ColorHandlerEvent.Item event) {
		event.getItemColors().register((itemstack, index) -> {
			final Item item = itemstack.getItem();
			if (item instanceof IDyeableItem) {
				return ((IDyeableItem) item).getColor(itemstack);
			}
			return 0;
		}, dyeableItems.toArray(new Item[dyeableItems.size()]));
	}
}
