package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.api.registry.CommandRegister;
import info.u_team.u_team_core.intern.command.UTeamCoreCommand;
import net.minecraft.Util;

public class UCoreCommands {
	
	private static final CommandRegister COMMANDS = Util.make(CommandRegister.create(), commands -> {
		commands.register(handler -> UTeamCoreCommand.register(handler.dispatcher()));
	});
	
	static void register() {
		COMMANDS.register();
	}
}
