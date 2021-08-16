package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.intern.command.UTeamCoreCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class UCoreCommands {
	
	private static void onRegisterCommands(RegisterCommandsEvent event) {
		new UTeamCoreCommand(event.getDispatcher());
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(UCoreCommands::onRegisterCommands);
	}
}
