package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.event.CommonEvents;
import info.u_team.u_team_core.api.event.CommonEvents.EndLevelTick;
import info.u_team.u_team_core.api.event.CommonEvents.EndServerTick;
import info.u_team.u_team_core.api.event.CommonEvents.LevelLoad;
import info.u_team.u_team_core.api.event.CommonEvents.LevelUnload;
import info.u_team.u_team_core.api.event.CommonEvents.RegisterEvent;
import info.u_team.u_team_core.api.event.CommonEvents.SetupEvent;
import info.u_team.u_team_core.api.event.CommonEvents.StartLevelTick;
import info.u_team.u_team_core.api.event.CommonEvents.StartServerTick;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.TickEvent.LevelTickEvent;
import net.neoforged.neoforge.event.TickEvent.Phase;
import net.neoforged.neoforge.event.TickEvent.ServerTickEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

public class NeoForgeCommonEventsHandler implements CommonEvents.Handler {
	
	@Override
	public void registerSetup(SetupEvent event, boolean forceMainThread) {
		registerModEvent(FMLCommonSetupEvent.class, modEvent -> {
			if (forceMainThread) {
				modEvent.enqueueWork(event::onSetup);
			} else {
				event.onSetup();
			}
		});
	}
	
	@Override
	public void registerRegister(RegisterEvent event) {
		registerModEvent(net.neoforged.neoforge.registries.RegisterEvent.class, modEvent -> {
			event.onRegister(modEvent.getRegistryKey());
		});
	}
	
	@Override
	public void registerStartServerTick(StartServerTick event) {
		registerForgeEvent(ServerTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.START) {
				event.onStartTick(forgeEvent.getServer());
			}
		});
	}
	
	@Override
	public void registerEndServerTick(EndServerTick event) {
		registerForgeEvent(ServerTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.END) {
				event.onEndTick(forgeEvent.getServer());
			}
		});
	}
	
	@Override
	public void registerStartLevelTick(StartLevelTick event) {
		registerForgeEvent(LevelTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.START && forgeEvent.level instanceof ServerLevel serverLevel) {
				event.onStartTick(serverLevel);
			}
		});
	}
	
	@Override
	public void registerEndLevelTick(EndLevelTick event) {
		registerForgeEvent(LevelTickEvent.class, forgeEvent -> {
			if (forgeEvent.phase == Phase.END && forgeEvent.level instanceof ServerLevel serverLevel) {
				event.onEndTick(serverLevel);
			}
		});
	}
	
	@Override
	public void registerLevelLoad(LevelLoad event) {
		registerForgeEvent(LevelEvent.Load.class, forgeEvent -> {
			if (forgeEvent.getLevel() instanceof ServerLevel serverLevel) {
				event.onLoad(serverLevel);
			}
		});
	}
	
	@Override
	public void registerLevelUnload(LevelUnload event) {
		registerForgeEvent(LevelEvent.Unload.class, forgeEvent -> {
			if (forgeEvent.getLevel() instanceof ServerLevel serverLevel) {
				event.onUnload(serverLevel);
			}
		});
	}
	
	private <T extends Event> void registerModEvent(Class<T> eventClass, Consumer<T> event) {
		BusRegister.registerMod(bus -> bus.addListener(EventPriority.NORMAL, false, eventClass, event));
	}
	
	private <T extends Event> void registerForgeEvent(Class<T> eventClass, Consumer<T> event) {
		BusRegister.registerForge(bus -> bus.addListener(EventPriority.NORMAL, false, eventClass, event));
	}
	
}
