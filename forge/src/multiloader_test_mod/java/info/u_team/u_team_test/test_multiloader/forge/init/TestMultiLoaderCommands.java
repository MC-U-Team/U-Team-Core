package info.u_team.u_team_test.test_multiloader.forge.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;
import info.u_team.u_team_test.test_multiloader.command.TestNetworkCommand;
import net.minecraftforge.event.RegisterCommandsEvent;

@Construct(modid = TestMultiLoaderMod.MODID)
public class TestMultiLoaderCommands implements ModConstruct {
	
	private static void onRegisterCommands(RegisterCommandsEvent event) {
		TestNetworkCommand.register(event.getDispatcher());
	}
	
	@Override
	public void construct() {
		BusRegister.registerForge(bus -> bus.addListener(TestMultiLoaderCommands::onRegisterCommands));
	}
	
}
