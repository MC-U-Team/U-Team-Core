package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.event.ClientEvents;
import info.u_team.u_team_core.api.event.ClientEvents.EndClientLevelTick;
import info.u_team.u_team_core.api.event.ClientEvents.EndClientTick;
import info.u_team.u_team_core.api.event.ClientEvents.ScreenAfterKeyPressed;
import info.u_team.u_team_core.api.event.ClientEvents.StartClientLevelTick;
import info.u_team.u_team_core.api.event.ClientEvents.StartClientTick;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.event.ScreenEvent.KeyPressed;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;

public class ForgeClientEventsHandler implements ClientEvents.Handler {
	
	@Override
	public void registerStartClientTick(StartClientTick event) {
		registerForgeEvent(ClientTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.START) {
				event.onStartTick(Minecraft.getInstance());
			}
		});
	}
	
	@Override
	public void registerEndClientTick(EndClientTick event) {
		registerForgeEvent(ClientTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.END) {
				event.onEndTick(Minecraft.getInstance());
			}
		});
	}
	
	@Override
	public void registerStartClientLevelTick(StartClientLevelTick event) {
		registerForgeEvent(LevelTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.START && forgeEvent.level instanceof ClientLevel clientLevel) {
				event.onStartTick(clientLevel);
			}
		});
	}
	
	@Override
	public void registerEndClientLevelTick(EndClientLevelTick event) {
		registerForgeEvent(LevelTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.END && forgeEvent.level instanceof ClientLevel clientLevel) {
				event.onEndTick(clientLevel);
			}
		});
	}
	
	@Override
	public void registerScreenAfterKeyPressed(ScreenAfterKeyPressed event) {
		registerForgeEvent(KeyPressed.Post.class, forgeEvent -> {
			if (event.onKeyPressed(forgeEvent.getScreen(), forgeEvent.getKeyCode(), forgeEvent.getScanCode(), forgeEvent.getModifiers())) {
				forgeEvent.setCanceled(true);
			}
		});
	}
	
	private <T extends Event> void registerForgeEvent(Class<T> eventClass, Consumer<T> event) {
		BusRegister.registerForge(bus -> bus.addListener(EventPriority.NORMAL, false, eventClass, event));
	}
}
