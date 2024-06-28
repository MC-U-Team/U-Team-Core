package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_core.util.registry.BusRegister;
import info.u_team.u_team_test.TestMod;

@Construct(modid = TestMod.MODID)
public class TestCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		TestArmorMaterials.register();
		TestBlockEntityTypes.register();
		TestBlocks.register();
		TestCreativeTabs.register();
		TestFluids.register();
		TestFluidTypes.register();
		TestItems.register();
		TestMenuTypes.register();
		
		BusRegister.registerMod(TestCapabilities::registerMod);
	}
	
}
