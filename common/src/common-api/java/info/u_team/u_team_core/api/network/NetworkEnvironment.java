package info.u_team.u_team_core.api.network;

import java.util.Set;

public enum NetworkEnvironment {
	
	CLIENT,
	SERVER;
	
	public static final Set<NetworkEnvironment> BOTH = Set.of(CLIENT, SERVER);
}
