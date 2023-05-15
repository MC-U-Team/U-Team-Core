package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderMod;

@Construct(modid = TestMultiLoaderMod.MODID)
public class TestMultiLoaderCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		TestMultiLoaderItems.register();
	}
	
}
