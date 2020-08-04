package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.intern.discord.UpdateDiscordEventHandler;
import info.u_team.u_team_core.util.registry.BusRegister;

public class UCoreClientBusRegister {
	
	public static void register() {
		BusRegister.registerMod(UpdateDiscordEventHandler::registerMod);
		BusRegister.registerMod(UCoreColors::registerMod);
		
		BusRegister.registerForge(UpdateDiscordEventHandler::registerForge);
	}
	
}
