package info.u_team.u_team_test.data.provider;

import info.u_team.u_team_core.data.CommonFluidTagsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_core.util.TagUtil;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.init.TestFluids;
import net.minecraft.core.HolderLookup;

public class TestFluidTagsProvider extends CommonFluidTagsProvider {
	
	public TestFluidTagsProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(HolderLookup.Provider provider) {
		tag(TagUtil.createFluidTag(TestMod.MODID, "test_fluid")).add(TestFluids.TEST_FLUID.get(), TestFluids.TEST_FLUID_FLOWING.get());
	}
	
}
