package info.u_team.u_team_test.test_multiloader;

import org.slf4j.Logger;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import net.fabricmc.api.ModInitializer;

public class TestMultiLoaderMod implements ModInitializer {
	
	public static final String MODID = TestMultiLoaderReference.MODID;
	public static final Logger LOGGER = TestMultiLoaderReference.LOGGER;
	
	@Override
	public void onInitialize() {
		LOGGER.info("Load multiloader test mod on fabric");
		AnnotationManager.callAnnotations(MODID);
	}
	
}
