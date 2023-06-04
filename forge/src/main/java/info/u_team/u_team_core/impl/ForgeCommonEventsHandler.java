package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.event.CommonEvents;
import info.u_team.u_team_core.api.event.CommonEvents.RegisterEvent;
import info.u_team.u_team_core.api.event.CommonEvents.SetupEvent;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ForgeCommonEventsHandler implements CommonEvents.Handler {
	
	@Override
	public void registerSetup(SetupEvent event) {
		registerModEvent(FMLCommonSetupEvent.class, modEvent -> {
			event.onSetup();
		});
	}
	
	@Override
	public void registerRegister(RegisterEvent event) {
		registerModEvent(net.minecraftforge.registries.RegisterEvent.class, modEvent -> {
			event.onRegister(modEvent.getRegistryKey());
		});
	}
	
	private <T extends Event> void registerModEvent(Class<T> eventClass, Consumer<T> event) {
		BusRegister.registerMod(bus -> bus.addListener(EventPriority.NORMAL, false, eventClass, event));
	}
	
}
