package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommandRegister;
import info.u_team.u_team_test.test_multiloader.command.TestNetworkCommand;
import net.minecraft.Util;

public class TestMultiLoaderCommands {
	
	public static final CommandRegister COMMANDS = Util.make(CommandRegister.create(), commands -> {
		commands.register(handler -> TestNetworkCommand.register(handler.dispatcher()));
	});
	
	static void register() {
		COMMANDS.register();
	}
}
