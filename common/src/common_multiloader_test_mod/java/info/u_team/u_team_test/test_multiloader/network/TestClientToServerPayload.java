package info.u_team.u_team_test.test_multiloader.network;

import info.u_team.u_team_core.api.network.NetworkHandlerEnvironment;

public class TestClientToServerPayload extends TestPayload {
	
	@Override
	public NetworkHandlerEnvironment handlerEnvironment() {
		return NetworkHandlerEnvironment.SERVER;
	}
}
