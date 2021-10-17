package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.api.dye.DyeableItemsRegistry;
import info.u_team.u_team_core.api.dye.DyeableItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class UCoreColors {
	
	private static void colorItem(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, index) -> {
			final var item = stack.getItem();
			if (item instanceof DyeableItem) {
				return ((DyeableItem) item).getColor(stack);
			}
			return 0;
		}, DyeableItemsRegistry.getDyeableItems().stream().toArray(Item[]::new));
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreColors::colorItem);
	}
}
