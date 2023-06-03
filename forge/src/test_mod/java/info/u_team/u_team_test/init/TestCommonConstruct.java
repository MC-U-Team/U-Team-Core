package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.TestMod;

@Construct(modid = TestMod.MODID)
public class TestCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		TestBlocks.register();
		TestMenuTypes.register();
		TestItems.register();
		TestBlockEntityTypes.register();
		
		BusRegister.registerMod(TestCreativeTabs::registerMod);
		BusRegister.registerMod(TestTiers::registerMod);
	}
	
}
