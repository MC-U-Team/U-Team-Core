package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.event.ClientEvents;
import info.u_team.u_team_core.api.event.ClientEvents.EndClientLevelTick;
import info.u_team.u_team_core.api.event.ClientEvents.EndClientTick;
import info.u_team.u_team_core.api.event.ClientEvents.ItemTooltip;
import info.u_team.u_team_core.api.event.ClientEvents.ScreenAfterKeyPressed;
import info.u_team.u_team_core.api.event.ClientEvents.StartClientLevelTick;
import info.u_team.u_team_core.api.event.ClientEvents.StartClientTick;
import info.u_team.u_team_core.event.ScreenEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class FabricClientEventsHandler implements ClientEvents.Handler {
	
	@Override
	public void registerStartClientTick(StartClientTick event) {
		ClientTickEvents.START_CLIENT_TICK.register(event::onStartTick);
	}
	
	@Override
	public void registerEndClientTick(EndClientTick event) {
		ClientTickEvents.END_CLIENT_TICK.register(event::onEndTick);
	}
	
	@Override
	public void registerStartClientLevelTick(StartClientLevelTick event) {
		ClientTickEvents.START_WORLD_TICK.register(event::onStartTick);
	}
	
	@Override
	public void registerEndClientLevelTick(EndClientLevelTick event) {
		ClientTickEvents.END_WORLD_TICK.register(event::onEndTick);
	}
	
	@Override
	public void registerScreenAfterKeyPressed(ScreenAfterKeyPressed event) {
		ScreenEvents.AFTER_KEY_PRESSED.register(event::onKeyPressed);
	}
	
	@Override
	public void registerItemTooltip(ItemTooltip event) {
		ItemTooltipCallback.EVENT.register(event::onTooltip);
	}
}
