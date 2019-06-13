package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.intern.command.CommandUTeamCore;
import info.u_team.u_team_core.registry.EventRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class UCoreCommands {
	
	public static void construct() {
		EventRegistry.registerEventHandler(UCoreCommands.class);
	}
	
	@SubscribeEvent
	public static void on(FMLServerStartingEvent event) {
		new CommandUTeamCore(event.getCommandDispatcher());
	}
	
}
