package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BusRegister;

public class TestClientBusRegister {
	
	public static void register() {
		BusRegister.registerMod(TestColors::registerMod);
		BusRegister.registerMod(TestKeys::registerMod);
		BusRegister.registerMod(TestModels::registerMod);
		BusRegister.registerMod(TestScreens::registerMod);
		
		BusRegister.registerForge(TestKeys::registerForge);
	}
	
}
