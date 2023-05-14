package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.intern.update.UpdateResolver;

@Construct(modid = UCoreMod.MODID)
public class UCoreCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		UpdateResolver.load();
		
		UCoreCommands.register();
	}
	
}
