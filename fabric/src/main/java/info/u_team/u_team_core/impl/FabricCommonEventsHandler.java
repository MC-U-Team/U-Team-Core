package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.event.CommonEvents;
import info.u_team.u_team_core.api.event.CommonEvents.RegisterEvent;
import info.u_team.u_team_core.api.event.CommonEvents.SetupEvent;
import info.u_team.u_team_core.event.SetupEvents;

public class FabricCommonEventsHandler implements CommonEvents.Handler {
	
	@Override
	public void registerSetup(SetupEvent event) {
		SetupEvents.COMMON_SETUP.register(event::onSetup);
	}
	
	@Override
	public void registerRegister(RegisterEvent event) {
		SetupEvents.REGISTER.register(event::onRegister);
	}
	
}
