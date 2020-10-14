package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.*;
import info.u_team.u_team_core.intern.discord.UpdateDiscordEventHandler;
import info.u_team.u_team_core.util.registry.BusRegister;

@Construct(modid = UCoreMod.MODID, client = true)
public class UCoreClientConstruct implements IModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(UpdateDiscordEventHandler::registerMod);
		BusRegister.registerMod(UCoreColors::registerMod);
		
		BusRegister.registerForge(UpdateDiscordEventHandler::registerForge);
	}
	
}