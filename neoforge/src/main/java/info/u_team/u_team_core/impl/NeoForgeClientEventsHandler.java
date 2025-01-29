package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.event.ClientEvents;
import info.u_team.u_team_core.api.event.ClientEvents.EndClientLevelTick;
import info.u_team.u_team_core.api.event.ClientEvents.EndClientTick;
import info.u_team.u_team_core.api.event.ClientEvents.ItemTooltip;
import info.u_team.u_team_core.api.event.ClientEvents.RenderBlockOutline;
import info.u_team.u_team_core.api.event.ClientEvents.ScreenAfterKeyPressed;
import info.u_team.u_team_core.api.event.ClientEvents.StartClientLevelTick;
import info.u_team.u_team_core.api.event.ClientEvents.StartClientTick;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class NeoForgeClientEventsHandler implements ClientEvents.Handler {
	
	@Override
	public void registerStartClientTick(StartClientTick event) {
		registerNeoForgeEvent(ClientTickEvent.Pre.class, neoForgeEvent -> {
			event.onStartTick(Minecraft.getInstance());
		});
	}
	
	@Override
	public void registerEndClientTick(EndClientTick event) {
		registerNeoForgeEvent(ClientTickEvent.Post.class, neoForgeEvent -> {
			event.onEndTick(Minecraft.getInstance());
		});
	}
	
	@Override
	public void registerStartClientLevelTick(StartClientLevelTick event) {
		registerNeoForgeEvent(LevelTickEvent.Pre.class, neoForgeEvent -> {
			if (neoForgeEvent.getLevel() instanceof final ClientLevel clientLevel) {
				event.onStartTick(clientLevel);
			}
		});
	}
	
	@Override
	public void registerEndClientLevelTick(EndClientLevelTick event) {
		registerNeoForgeEvent(LevelTickEvent.Post.class, neoForgeEvent -> {
			if (neoForgeEvent.getLevel() instanceof final ClientLevel clientLevel) {
				event.onEndTick(clientLevel);
			}
		});
	}
	
	@Override
	public void registerScreenAfterKeyPressed(ScreenAfterKeyPressed event) {
		registerNeoForgeEvent(KeyPressed.Post.class, neoForgeEvent -> {
			if (event.onKeyPressed(neoForgeEvent.getScreen(), neoForgeEvent.getKeyCode(), neoForgeEvent.getScanCode(), neoForgeEvent.getModifiers())) {
				neoForgeEvent.setCanceled(true);
			}
		});
	}
	
	@Override
	public void registerItemTooltip(ItemTooltip event) {
		registerNeoForgeEvent(ItemTooltipEvent.class, neoForgeEvent -> {
			event.onTooltip(neoForgeEvent.getItemStack(), neoForgeEvent.getFlags(), neoForgeEvent.getToolTip());
		});
	}
	
	@Override
	public void registerRenderBlockOutline(RenderBlockOutline event) {
		registerNeoForgeEvent(RenderHighlightEvent.Block.class, neoForgeEvent -> {
			if (!event.onRenderBlockOutline(neoForgeEvent.getLevelRenderer(), neoForgeEvent.getCamera(), neoForgeEvent.getTarget(), neoForgeEvent.getDeltaTracker(), neoForgeEvent.getPoseStack(), neoForgeEvent.getMultiBufferSource())) {
				neoForgeEvent.setCanceled(true);
			}
		});
	}
	
	private <T extends Event> void registerNeoForgeEvent(Class<T> eventClass, Consumer<T> event) {
		BusRegister.registerNeoForge(bus -> bus.addListener(EventPriority.NORMAL, false, eventClass, event));
	}
	
}
