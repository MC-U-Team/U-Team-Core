package info.u_team.u_team_test.test_multiloader.messages;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.api.network.NetworkContext;

public class TestMessageHandler {
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	public static void handle(TestMessage message, NetworkContext context) {
		LOGGER.info("Received message '{}' to side '{}' from '{}' on thread '{}'", message, context.getEnvironment(), context.getPlayer(), Thread.currentThread().getName());
	}
	
}
