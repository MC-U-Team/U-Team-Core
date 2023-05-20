package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.event.ClientEvents;
import info.u_team.u_team_core.api.event.ClientEvents.EndClientTick;
import info.u_team.u_team_core.api.event.ClientEvents.StartClientTick;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FabricClientEventsHandler implements ClientEvents.Handler {
	
	@Override
	public void registerStartClientTick(StartClientTick event) {
		ClientTickEvents.START_CLIENT_TICK.register(event::onStartTick);
	}
	
	@Override
	public void registerEndClientTick(EndClientTick event) {
		ClientTickEvents.END_CLIENT_TICK.register(event::onEndTick);
	}
}
