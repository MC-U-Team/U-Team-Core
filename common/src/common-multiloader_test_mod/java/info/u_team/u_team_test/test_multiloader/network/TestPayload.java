package info.u_team.u_team_test.test_multiloader.network;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_test.test_multiloader.network.TestPayload.TestMessage;
import net.minecraft.network.FriendlyByteBuf;

public abstract class TestPayload implements NetworkPayload<TestMessage> {
	
	public static record TestMessage(String value) {
	}
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	@Override
	public void write(TestMessage message, FriendlyByteBuf buffer) {
		buffer.writeUtf(message.value());
	}
	
	@Override
	public TestMessage read(FriendlyByteBuf buffer) {
		return new TestMessage(buffer.readUtf());
	}
	
	@Override
	public void handle(TestMessage message, NetworkContext context) {
		LOGGER.info("Received message '{}' to side '{}' from '{}' on thread '{}'", message, context.getEnvironment(), context.getPlayer(), Thread.currentThread().getName());
		context.executeOnMainThread(() -> {
			LOGGER.info("Executed on main thread: {}", message);
		});
	}
	
}
