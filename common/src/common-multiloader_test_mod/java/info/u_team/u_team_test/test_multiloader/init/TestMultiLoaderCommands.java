package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommandRegister;
import info.u_team.u_team_test.test_multiloader.command.TestNetworkCommand;

public class TestMultiLoaderCommands {
	
	public static void register() {
		CommandRegister.register(handler -> {
			TestNetworkCommand.register(handler.dispatcher());
		});
	}
	
}
