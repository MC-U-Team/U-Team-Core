package info.u_team.u_team_test.test_multiloader.forge.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;
import info.u_team.u_team_test.test_multiloader.command.TestNetworkCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;

@Construct(modid = TestMultiLoaderMod.MODID)
public class TestMultiLoaderCommands implements ModConstruct {
	
	private static void onRegisterCommands(RegisterCommandsEvent event) {
		TestNetworkCommand.register(event.getDispatcher());
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(TestMultiLoaderCommands::onRegisterCommands);
	}
	
	@Override
	public void construct() {
		BusRegister.registerForge(TestMultiLoaderCommands::registerForge);
	}
	
}
