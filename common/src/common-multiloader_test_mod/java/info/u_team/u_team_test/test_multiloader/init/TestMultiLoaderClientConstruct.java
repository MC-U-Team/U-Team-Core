package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;

@Construct(modid = TestMultiLoaderReference.MODID, client = true)
public class TestMultiLoaderClientConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		TestMultiLoaderKeyMappings.register();
	}
	
}
