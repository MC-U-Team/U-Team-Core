package info.u_team.u_team_test.test_multiloader.init.forge;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;

@Construct(modid = TestMultiLoaderReference.MODID)
public class TestMultiLoaderForgeCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		TestMultiLoaderForgeGlobalLootModifierSerializers.register();
	}
	
}
