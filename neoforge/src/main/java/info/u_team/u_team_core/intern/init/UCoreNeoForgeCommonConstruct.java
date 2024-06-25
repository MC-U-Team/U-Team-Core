package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;

@Construct(modid = UCoreMod.MODID, priority = 2000)
public class UCoreNeoForgeCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(UCoreNetworkNeoForge::registerMod);
		BusRegister.registerMod(UCoreNeoForgeCapabilities::registerMod);
	}
}
