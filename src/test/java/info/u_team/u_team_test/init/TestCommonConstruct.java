package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.TestMod;

@Construct(modid = TestMod.MODID)
public class TestCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		BusRegister.registerMod(TestBlocks::registerMod);
		BusRegister.registerMod(TestMenuTypes::registerMod);
		BusRegister.registerMod(TestMobEffects::registerMod);
		BusRegister.registerMod(TestEnchantments::registerMod);
		BusRegister.registerMod(TestSpawnPlacementRegistries::registerMod);
		BusRegister.registerMod(TestEntityTypes::registerMod);
		BusRegister.registerMod(TestGlobalEntityAttributes::registerMod);
		BusRegister.registerMod(TestGlobalLootModifierSerializers::registerMod);
		BusRegister.registerMod(TestItems::registerMod);
		BusRegister.registerMod(TestPotions::registerMod);
		BusRegister.registerMod(TestSoundEvents::registerMod);
		BusRegister.registerMod(TestBlockEntityTypes::registerMod);
		
		BusRegister.registerForge(TestBiomeLoadingAdditions::registerForge);
	}
	
}
