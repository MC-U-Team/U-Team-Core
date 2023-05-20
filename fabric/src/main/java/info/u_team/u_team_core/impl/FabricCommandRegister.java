package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.CommandRegister;
import info.u_team.u_team_core.impl.common.CommonCommandRegister;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FabricCommandRegister extends CommonCommandRegister {
	
	FabricCommandRegister() {
	}
	
	@Override
	public void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			final CommandHandler handler = new CommandHandler(dispatcher, registryAccess, environment);
			entries.forEach(entry -> entry.accept(handler));
		});
	}
	
	public static class Factory implements CommandRegister.Factory {
		
		@Override
		public CommandRegister create() {
			return new FabricCommandRegister();
		}
	}
}
