package info.u_team.u_team_test.integration;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.api.integration.Integration;
import info.u_team.u_team_core.api.integration.ModIntegration;
import info.u_team.u_team_test.TestMod;

@Integration(modid = TestMod.MODID, integration = "xyz")
public class TestIntegrationNotCall implements ModIntegration {
	
	public static final Logger LOGGER = LogUtils.getLogger();
	
	static {
		LOGGER.error(LogUtils.FATAL_MARKER, "Integration not call is cinit!");
	}
	
	@Override
	public void construct() {
		LOGGER.error(LogUtils.FATAL_MARKER, "Integration not call is constructed! THIS SHOULD NEVER HAPPEN!!");
		throw new RuntimeException("Integration should not be constructed");
	}
	
}
