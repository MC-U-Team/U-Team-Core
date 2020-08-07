package info.u_team.u_team_test.integration;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.api.integration.*;

@Integration("uteamcore")
public class TestIntegrationCall implements IModIntegration {
	
	public static final Logger LOGGER = LogManager.getLogger("IntegrationCall");
	
	@Override
	public void construct() {
		LOGGER.info("Integration call is constructed!");
	}
	
}
