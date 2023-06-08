package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.ColorProviderRegister;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.impl.common.CommonColorProviderRegister;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class FabricColorProviderRegister extends CommonColorProviderRegister {
	
	FabricColorProviderRegister() {
	}
	
	@Override
	public void register() {
		SetupEvents.COMMON_SETUP.register(this::setup);
	}
	
	private void setup() {
		blockEntries.forEach(handler -> {
			handler.registerColor(() -> Minecraft.getInstance().getBlockColors(), (color, entries) -> {
				ColorProviderRegistry.BLOCK.register(color, entries.toArray(Block[]::new));
			});
		});
		itemEntries.forEach(handler -> {
			handler.registerColor(() -> Minecraft.getInstance().itemColors, () -> Minecraft.getInstance().getBlockColors(), (color, entries) -> {
				ColorProviderRegistry.ITEM.register(color, entries.toArray(ItemLike[]::new));
			});
		});
	}
	
	public static class Factory implements ColorProviderRegister.Factory {
		
		@Override
		public ColorProviderRegister create() {
			return new FabricColorProviderRegister();
		}
	}
	
}
