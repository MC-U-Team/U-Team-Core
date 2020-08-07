package info.u_team.u_team_test.integration;

import org.apache.logging.log4j.*;

import info.u_team.u_team_core.api.integration.*;

@Integration("xyz")
public class TestIntegrationNotCall implements IModIntegration {
	
	public static final Logger LOGGER = LogManager.getLogger("IntegrationNotCall");
	
	@Override
	public void construct() {
		LOGGER.error("Integration not call is constructed! THIS SHOULD NEVER HAPPEN!!");
		throw new RuntimeException("Integration should not be constructed");
	}
	
}
