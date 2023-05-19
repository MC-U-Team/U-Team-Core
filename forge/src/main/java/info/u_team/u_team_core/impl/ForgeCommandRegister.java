package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CommandRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class ForgeCommandRegister implements CommandRegister {
	
	@Override
	public void registerCommand(Consumer<CommandHandler> consumer) {
		BusRegister.registerForge(bus -> bus.addListener(EventPriority.NORMAL, false, RegisterCommandsEvent.class, event -> {
			consumer.accept(new CommandHandler(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection()));
		}));
	}
	
}
