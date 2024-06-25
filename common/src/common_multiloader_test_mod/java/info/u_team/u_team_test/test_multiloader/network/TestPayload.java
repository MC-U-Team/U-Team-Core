package info.u_team.u_team_test.test_multiloader.network;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_test.test_multiloader.network.TestPayload.TestMessage;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public abstract class TestPayload implements NetworkPayload<TestMessage> {
	
	public static record TestMessage(String value) {
	}
	
	private static final Logger LOGGER = LogUtils.getLogger();
	
	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, TestMessage> streamCodec() {
		return ByteBufCodecs.STRING_UTF8.map(TestMessage::new, TestMessage::value);
	}
	
	@Override
	public void handle(TestMessage message, NetworkContext context) {
		LOGGER.info("Received message '{}' to side '{}' from '{}' on thread '{}'", message, context.getEnvironment(), context.getPlayer(), Thread.currentThread().getName());
		context.executeOnMainThread(() -> {
			LOGGER.info("Executed on main thread: {}", message);
		});
	}
	
}
