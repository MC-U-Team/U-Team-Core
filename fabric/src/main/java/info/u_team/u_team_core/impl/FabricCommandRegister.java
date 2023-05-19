package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CommandRegister;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FabricCommandRegister implements CommandRegister {
	
	@Override
	public void registerCommand(Consumer<CommandHandler> consumer) {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			consumer.accept(new CommandHandler(dispatcher, registryAccess, environment));
		});
	}
	
}
