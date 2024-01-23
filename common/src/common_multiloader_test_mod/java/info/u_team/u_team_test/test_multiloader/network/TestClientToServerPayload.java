package info.u_team.u_team_test.test_multiloader.network;

import java.util.Set;

import info.u_team.u_team_core.api.network.NetworkEnvironment;

public class TestClientToServerPayload extends TestPayload {
	
	@Override
	public Set<NetworkEnvironment> getHandlerEnvironment() {
		return Set.of(NetworkEnvironment.SERVER);
	}
}
