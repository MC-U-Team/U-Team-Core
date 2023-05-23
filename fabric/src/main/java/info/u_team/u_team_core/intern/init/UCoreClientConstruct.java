package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;

@Construct(modid = UCoreMod.MODID, client = true)
public class UCoreClientConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		UCoreColors.register();
	}
	
}
