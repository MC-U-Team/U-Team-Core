package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.BusRegister;

public class TestCommonBusRegister {
	
	public static void register() {
		BusRegister.registerMod(TestBiomes::registerMod);
		BusRegister.registerMod(TestBlocks::registerMod);
		BusRegister.registerMod(TestContainers::registerMod);
		BusRegister.registerMod(TestEffects::registerMod);
		BusRegister.registerMod(TestEnchantments::registerMod);
		BusRegister.registerMod(TestEntityTypes::registerMod);
		BusRegister.registerMod(TestGlobalLootModifiers::registerMod);
		BusRegister.registerMod(TestItems::registerMod);
		BusRegister.registerMod(TestModDimensions::registerMod);
		BusRegister.registerMod(TestPotions::registerMod);
		BusRegister.registerMod(TestSounds::registerMod);
		BusRegister.registerMod(TestTileEntityTypes::registerMod);
	}
	
}
