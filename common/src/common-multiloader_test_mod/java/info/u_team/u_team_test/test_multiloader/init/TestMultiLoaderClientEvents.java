package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.event.ClientEvents;
import info.u_team.u_team_test.test_multiloader.messages.TestClientToServerMessage;
import net.minecraft.client.Minecraft;

public class TestMultiLoaderClientEvents {
	
	private static void onEndClientTick(Minecraft minecraft) {
		while (TestMultiLoaderKeyMappings.TEST_NETWORK_EXPLICIT_C2S.get().consumeClick()) {
			TestMultiLoaderNetwork.NETWORK.sendToServer(new TestClientToServerMessage("Hello server"));
		}
	}
	
	static void register() {
		ClientEvents.registerEndClientTick(TestMultiLoaderClientEvents::onEndClientTick);
	}
	
}
