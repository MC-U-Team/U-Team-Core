package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.network.TestClientToServerPayload;
import info.u_team.u_team_test.test_multiloader.network.TestPayload.TestMessage;
import info.u_team.u_team_test.test_multiloader.network.TestServerToClientPayload;
import net.minecraft.resources.ResourceLocation;

public class TestMultiLoaderNetwork {
	
	private static final NetworkHandler NETWORK = NetworkHandler.create(ResourceLocation.fromNamespaceAndPath(TestMultiLoaderReference.MODID, "network"), TestMultiLoaderReference.PROTOCOL_VERSION);
	
	public static final NetworkMessage<TestMessage> TEST_SERVER_TO_CLIENT_MESSAGE = NETWORK.register("s2c", new TestServerToClientPayload());
	public static final NetworkMessage<TestMessage> TEST_CLIENT_TO_SERVER_MESSAGE = NETWORK.register("c2s", new TestClientToServerPayload());
	
	static void register() {
		NETWORK.register();
	}
	
}
