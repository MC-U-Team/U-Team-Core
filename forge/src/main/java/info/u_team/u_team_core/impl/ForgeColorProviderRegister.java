package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.ColorProviderRegister;
import info.u_team.u_team_core.impl.common.CommonColorProviderRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;

public class ForgeColorProviderRegister extends CommonColorProviderRegister {
	
	ForgeColorProviderRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> {
			bus.addListener(this::registerBlockColor);
			bus.addListener(this::registerItemColor);
		});
	}
	
	private void registerBlockColor(RegisterColorHandlersEvent.Block event) {
		blockEntries.forEach(handler -> {
			handler.registerColor(event::getBlockColors, (color, entries) -> {
				event.register(color, entries.toArray(Block[]::new));
			});
		});
	}
	
	private void registerItemColor(RegisterColorHandlersEvent.Item event) {
		itemEntries.forEach(handler -> {
			handler.registerColor(event::getItemColors, event::getBlockColors, (color, entries) -> {
				event.register(color, entries.toArray(ItemLike[]::new));
			});
		});
	}
	
	public static class Factory implements ColorProviderRegister.Factory {
		
		@Override
		public ColorProviderRegister create() {
			return new ForgeColorProviderRegister();
		}
	}
	
}
