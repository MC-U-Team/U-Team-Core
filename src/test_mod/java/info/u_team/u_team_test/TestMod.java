package info.u_team.u_team_test;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;

@Mod(TestMod.MODID)
public class TestMod {
	
	public static final String MODID = "uteamtest";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public TestMod() {
		JarSignVerifier.checkSigned(MODID);
		
		LOGGER.info("--------------------------------------- LOADING TEST MOD ---------------------------------------");
		
		AnnotationManager.callAnnotations(MODID);
	}
}
