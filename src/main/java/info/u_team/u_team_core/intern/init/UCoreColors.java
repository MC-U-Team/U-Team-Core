package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.api.dye.DyeableItem;
import info.u_team.u_team_core.api.dye.DyeableItemsRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class UCoreColors {
	
	private static void colorItem(ColorHandlerEvent.Item event) {
		event.getItemColors().register((stack, index) -> {
			if (stack.getItem() instanceof final DyeableItem dyeable) {
				return dyeable.getColor(stack);
			}
			return 0;
		}, DyeableItemsRegistry.getDyeableItems().stream().toArray(Item[]::new));
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreColors::colorItem);
	}
}
