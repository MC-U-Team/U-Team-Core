package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonItemModelProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderItems;

public class TestMultiLoaderItemModelProvider extends CommonItemModelProvider {
	
	public TestMultiLoaderItemModelProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	public void register() {
		// Items
		simpleGenerated(TestMultiLoaderItems.TEST.get());
	}
	
}
