package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonBlockTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.item.tier.VanillaTierTags;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlocks;
import net.minecraft.core.HolderLookup;

public class TestMultiLoaderBlockTagsProvider extends CommonBlockTagsProvider {
	
	public TestMultiLoaderBlockTagsProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(HolderLookup.Provider provider) {
		// Add block tags for mining
		tag(VanillaTierTags.MINEABLE_WITH_PICKAXE).add(TestMultiLoaderBlocks.TEST.get());
		tag(VanillaTierTags.NEEDS_IRON_TOOL).add(TestMultiLoaderBlocks.TEST.get());
	}
	
}
