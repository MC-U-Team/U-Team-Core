package info.u_team.u_team_test.integration;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.api.integration.*;
import info.u_team.u_team_test.TestMod;

@Integration(modid = TestMod.MODID, client = true, integration = "uteamcore")
public class TestIntegrationClientCall implements IModIntegration {
	
	public static final Logger LOGGER = LogManager.getLogger("IntegrationClientCall");
	
	static {
		LOGGER.info("Integration client call is cinit!");
	}
	
	@Override
	public void construct() {
		LOGGER.info("Integration client call is constructed!");
	}
	
}
