package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.util.registry.BusRegister;

public class UCoreCommonBusRegister {
	
	public static void register() {
		BusRegister.registerMod(UCoreNetwork::registerMod);
		BusRegister.registerMod(UCoreRecipeSerializers::registerMod);
	}
}
