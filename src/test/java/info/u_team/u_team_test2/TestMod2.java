package info.u_team.u_team_test2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import info.u_team.u_team_core.util.annotation.AnnotationManager;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;

@Mod(TestMod2.MODID)
public class TestMod2 {
	
	public static final String MODID = "uteamtest2";
	public static final Logger LOGGER = LogManager.getLogger("UTeamTest2");
	
	public TestMod2() {
		JarSignVerifier.checkSigned(MODID);
		
		LOGGER.info("-------------------------------------- LOADING TEST MOD 2 -------------------------------------");
		
		AnnotationManager.callAnnotations(MODID);
	}
}
