package info.u_team.u_team_test.test_multiloader.data.provider;

import info.u_team.u_team_core.data.CommonSoundProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderSoundEvents;
import net.minecraft.resources.ResourceLocation;

public class TestMultiLoaderSoundProvider extends CommonSoundProvider {
	
	public TestMultiLoaderSoundProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register() {
		add(TestMultiLoaderSoundEvents.TEST_ENDERPEARL_USE, definition().with(sound(new ResourceLocation(TestMultiLoaderReference.MODID, "test_ender_pearl_use"))));
	}
	
}
