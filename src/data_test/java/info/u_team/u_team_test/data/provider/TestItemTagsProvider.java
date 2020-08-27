package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.*;
import info.u_team.u_team_test.init.TestTags.*;

public class TestItemTagsProvider extends CommonItemTagsProvider {
	
	public TestItemTagsProvider(GenerationData data, CommonBlockTagsProvider blockProvider) {
		super(data, blockProvider);
	}
	
	@Override
	protected void registerTags() {
		copy(Blocks.TEST_TAG_1, Items.TEST_TAG_1);
		copy(Blocks.TEST_TAG_2, Items.TEST_TAG_2);
	}
	
}
