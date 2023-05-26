package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;

@Construct(modid = UCoreReference.MODID)
public class UCoreCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		UCoreLootFunctions.register();
		UCoreRecipeSerializers.register();
		
		UCoreCommands.register();
		UCoreNetwork.register();
	}
	
}
