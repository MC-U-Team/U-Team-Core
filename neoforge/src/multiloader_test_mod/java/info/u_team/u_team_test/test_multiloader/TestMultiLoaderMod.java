package info.u_team.u_team_test.test_multiloader;

import org.slf4j.Logger;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import net.neoforged.fml.common.Mod;

@Mod(TestMultiLoaderMod.MODID)
public class TestMultiLoaderMod {
	
	public static final String MODID = TestMultiLoaderReference.MODID;
	public static final Logger LOGGER = TestMultiLoaderReference.LOGGER;
	
	public TestMultiLoaderMod() {
		LOGGER.info("Load multiloader test mod on neoforge");
		AnnotationManager.callAnnotations(MODID);
	}
	
}
