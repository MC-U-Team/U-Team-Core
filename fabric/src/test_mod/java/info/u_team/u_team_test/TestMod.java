package info.u_team.u_team_test;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import net.fabricmc.api.ModInitializer;

public class TestMod implements ModInitializer {
	
	public static final String MODID = "uteamtest";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	@Override
	public void onInitialize() {
		AnnotationManager.callAnnotations(MODID);
	}
	
}
