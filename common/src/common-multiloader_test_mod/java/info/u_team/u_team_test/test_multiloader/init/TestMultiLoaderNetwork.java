package info.u_team.u_team_test.test_multiloader.init;

import java.util.Optional;

import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.messages.TestMessageHandler;
import info.u_team.u_team_test.test_multiloader.messages.TestServerToClientMessage;
import net.minecraft.resources.ResourceLocation;

public class TestMultiLoaderNetwork {
	
	public static final NetworkHandler NETWORK = NetworkHandler.create(new ResourceLocation(TestMultiLoaderReference.MODID, "network"));
	
	public static void register() {
		NETWORK.registerMessage(0, TestServerToClientMessage.class, TestServerToClientMessage::encode, TestServerToClientMessage::decode, TestMessageHandler::handle, Optional.of(NetworkEnvironment.CLIENT));
	}
	
}
