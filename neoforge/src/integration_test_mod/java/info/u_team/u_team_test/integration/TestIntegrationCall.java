package info.u_team.u_team_test.integration;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.api.integration.Integration;
import info.u_team.u_team_core.api.integration.ModIntegration;
import info.u_team.u_team_test.TestMod;

@Integration(modid = TestMod.MODID, integration = "uteamcore")
public class TestIntegrationCall implements ModIntegration {
	
	public static final Logger LOGGER = LogUtils.getLogger();
	
	static {
		LOGGER.info("Integration call is cinit!");
	}
	
	@Override
	public void construct() {
		LOGGER.info("Integration call is constructed!");
	}
	
}
