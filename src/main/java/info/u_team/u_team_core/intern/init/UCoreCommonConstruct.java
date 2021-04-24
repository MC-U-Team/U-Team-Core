package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.IModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;

@Construct(modid = UCoreMod.MODID)
public class UCoreCommonConstruct implements IModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(UCoreNetwork::registerMod);
		BusRegister.registerMod(UCoreRecipeSerializers::registerMod);
		BusRegister.registerMod(UCoreLazySpawnEggs::registerMod);
		BusRegister.registerMod(UCoreLootFunctions::registerMod);
		
		BusRegister.registerForge(UCoreCommands::registerForge);
	}
}
