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
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ForgeCommonEventsHandler implements CommonEvents.Handler {
	
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
		registerModEvent(net.minecraftforge.registries.RegisterEvent.class, modEvent -> {
			event.onRegister(modEvent.getRegistryKey());
		});
	}
	
	@Override
	public void registerStartServerTick(StartServerTick event) {
		registerForgeEvent(ServerTickEvent.Pre.class, forgeEvent -> {
			event.onStartTick(forgeEvent.getServer());
		});
	}
	
	@Override
	public void registerEndServerTick(EndServerTick event) {
		registerForgeEvent(ServerTickEvent.Post.class, forgeEvent -> {
			event.onEndTick(forgeEvent.getServer());
		});
	}
	
	@Override
	public void registerStartLevelTick(StartLevelTick event) {
		registerForgeEvent(LevelTickEvent.Pre.class, forgeEvent -> {
			if (forgeEvent.level instanceof ServerLevel serverLevel) {
				event.onStartTick(serverLevel);
			}
		});
	}
	
	@Override
	public void registerEndLevelTick(EndLevelTick event) {
		registerForgeEvent(LevelTickEvent.Post.class, forgeEvent -> {
			if (forgeEvent.level instanceof ServerLevel serverLevel) {
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
