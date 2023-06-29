package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.event.CommonEvents;
import info.u_team.u_team_core.api.event.CommonEvents.EndLevelTick;
import info.u_team.u_team_core.api.event.CommonEvents.EndServerTick;
import info.u_team.u_team_core.api.event.CommonEvents.LevelLoad;
import info.u_team.u_team_core.api.event.CommonEvents.LevelUnload;
import info.u_team.u_team_core.api.event.CommonEvents.RegisterEvent;
import info.u_team.u_team_core.api.event.CommonEvents.SetupEvent;
import info.u_team.u_team_core.api.event.CommonEvents.StartLevelTick;
import info.u_team.u_team_core.api.event.CommonEvents.StartServerTick;
import info.u_team.u_team_core.event.SetupEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public class FabricCommonEventsHandler implements CommonEvents.Handler {
	
	@Override
	public void registerSetup(SetupEvent event) {
		SetupEvents.COMMON_SETUP.register(event::onSetup);
	}
	
	@Override
	public void registerRegister(RegisterEvent event) {
		SetupEvents.REGISTER.register(event::onRegister);
	}
	
	@Override
	public void registerStartServerTick(StartServerTick event) {
		ServerTickEvents.START_SERVER_TICK.register(event::onStartTick);
	}
	
	@Override
	public void registerEndServerTick(EndServerTick event) {
		ServerTickEvents.END_SERVER_TICK.register(event::onEndTick);
	}
	
	@Override
	public void registerStartLevelTick(StartLevelTick event) {
		ServerTickEvents.START_WORLD_TICK.register(event::onStartTick);
	}
	
	@Override
	public void registerEndLevelTick(EndLevelTick event) {
		ServerTickEvents.END_WORLD_TICK.register(event::onEndTick);
	}
	
	@Override
	public void registerLevelLoad(LevelLoad event) {
		ServerWorldEvents.LOAD.register((server, level) -> event.onLoad(level));
	}
	
	@Override
	public void registerLevelUnload(LevelUnload event) {
		ServerWorldEvents.UNLOAD.register((server, level) -> event.onUnload(level));
	}
	
}
