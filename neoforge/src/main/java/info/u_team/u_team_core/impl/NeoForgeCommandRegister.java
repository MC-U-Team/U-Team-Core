package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.CommandRegister;
import info.u_team.u_team_core.impl.common.CommonCommandRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class NeoForgeCommandRegister extends CommonCommandRegister {
	
	NeoForgeCommandRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerForge(bus -> bus.addListener(this::registerCommand));
	}
	
	private void registerCommand(RegisterCommandsEvent event) {
		final CommandHandler handler = new CommandHandler(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
		entries.forEach(entry -> entry.accept(handler));
	}
	
	public static class Factory implements CommandRegister.Factory {
		
		@Override
		public CommandRegister create() {
			return new NeoForgeCommandRegister();
		}
	}
}
