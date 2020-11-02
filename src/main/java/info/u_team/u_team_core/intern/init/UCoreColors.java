package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.api.dye.*;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class UCoreColors {
	
	private static void colorItem(ColorHandlerEvent.Item event) {
		event.getItemColors().register((itemstack, index) -> {
			final Item item = itemstack.getItem();
			if (item instanceof IDyeableItem) {
				return ((IDyeableItem) item).getColor(itemstack);
			}
			return 0;
		}, DyeableItemsRegistry.getDyeableItems().stream().toArray(Item[]::new));
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreColors::colorItem);
	}
}
