package info.u_team.u_team_test.test_multiloader.neoforge.init;

import info.u_team.u_team_core.api.construct.Construct;
import info.u_team.u_team_core.api.construct.ModConstruct;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;

@Construct(modid = TestMultiLoaderReference.MODID)
public class NeoForgeTestMultiLoaderCommonConstruct implements ModConstruct {
	
	@Override
	public void construct() {
		NeoForgeTestMultiLoaderCapabilities.register();
		NeoForgeTestMultiLoaderGlobalLootModifierSerializers.register();
	}
	
}
