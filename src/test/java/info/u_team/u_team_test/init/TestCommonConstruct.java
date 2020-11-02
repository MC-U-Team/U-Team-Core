package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.construct.*;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.TestMod;

@Construct(modid = TestMod.MODID)
public class TestCommonConstruct implements IModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(TestBlocks::registerMod);
		BusRegister.registerMod(TestContainers::registerMod);
		BusRegister.registerMod(TestEffects::registerMod);
		BusRegister.registerMod(TestEnchantments::registerMod);
		BusRegister.registerMod(TestEntityTypes::registerMod);
		BusRegister.registerMod(TestGlobalEntityTypeAttributes::registerMod);
		BusRegister.registerMod(TestGlobalLootModifiers::registerMod);
		BusRegister.registerMod(TestItems::registerMod);
		BusRegister.registerMod(TestPotions::registerMod);
		BusRegister.registerMod(TestSounds::registerMod);
		BusRegister.registerMod(TestTileEntityTypes::registerMod);
	}
	
}
