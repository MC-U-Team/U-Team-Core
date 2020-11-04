package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.item.USpawnEggItem;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class UCoreClientLazySpawnEggs {
	
	private static void colorItem(ColorHandlerEvent.Item event) {
		USpawnEggItem.LAZY_EGGS.forEach(pair -> {
			final USpawnEggItem item = pair.getValue();
			
			event.getItemColors().register((stack, color) -> {
				return item.getColor(color);
			}, item);
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UCoreClientLazySpawnEggs::colorItem);
	}
}
