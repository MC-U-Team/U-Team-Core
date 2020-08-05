package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BusRegister;

public class TestCommonBusRegister {
	
	public static void register() {
		BusRegister.registerMod(TestBiomes::registerMod);
		BusRegister.registerMod(TestBlocks::registerMod);
		BusRegister.registerMod(TestContainers::registerMod);
		BusRegister.registerMod(TestEffects::registerMod);
		BusRegister.registerMod(TestEnchantments::registerMod);
		BusRegister.registerMod(TestEntityTypes::register);
		BusRegister.registerMod(TestGlobalLootModifiers::register);
		BusRegister.registerMod(TestItems::register);
		BusRegister.registerMod(TestModDimensions::register);
		BusRegister.registerMod(TestPotions::register);
		BusRegister.registerMod(TestSounds::register);
		BusRegister.registerMod(TestTileEntityTypes::register);
	}
	
}
