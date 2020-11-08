package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.api.dye.*;
import info.u_team.u_team_core.item.USpawnEggItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class UCoreColors {
	
	private static void colorItem(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, index) -> {
			final Item item = stack.getItem();
			if (item instanceof IDyeableItem) {
				return ((IDyeableItem) item).getColor(stack);
			}
			return 0;
		}, DyeableItemsRegistry.getDyeableItems().stream().toArray(Item[]::new));
		
		USpawnEggItem.LAZY_EGGS.forEach(pair -> {
			final USpawnEggItem item = pair.getValue();
			
			event.getItemColors().register((stack, color) -> {
				return item.getColor(color);
			}, item);
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreColors::colorItem);
	}
}
