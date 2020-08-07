package info.u_team.u_team_test;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.integration.IntegrationManager;
import info.u_team.u_team_core.util.verify.JarSignVerifier;
import info.u_team.u_team_test.init.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(TestMod.MODID)
public class TestMod {
	
	public static final String MODID = "uteamtest";
	public static final Logger LOGGER = LogManager.getLogger("UTeamTest");
	
	public TestMod() {
		JarSignVerifier.checkSigned(MODID);
		
		LOGGER.info("--------------------------------------- LOADING TEST MOD ---------------------------------------");
		
		TestCommonBusRegister.register();
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> TestClientBusRegister::register);
		
		IntegrationManager.constructIntegrations(MODID);
	}
}
