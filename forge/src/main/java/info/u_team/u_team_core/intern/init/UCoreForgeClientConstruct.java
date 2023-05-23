package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;

@Construct(modid = UCoreMod.MODID, client = true, priority = 2000)
public class UCoreForgeClientConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(UCorePostMenuScreensEvent::registerMod);
	}
	
}
