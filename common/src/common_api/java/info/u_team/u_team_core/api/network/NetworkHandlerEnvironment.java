package info.u_team.u_team_core.api.network;

import java.util.Set;

public enum NetworkHandlerEnvironment {
	
	CLIENT(NetworkEnvironment.CLIENT),
	SERVER(NetworkEnvironment.SERVER),
	BOTH(NetworkEnvironment.CLIENT, NetworkEnvironment.SERVER);
	
	private final Set<NetworkEnvironment> environments;
	
	private NetworkHandlerEnvironment(NetworkEnvironment... environments) {
		this.environments = Set.of(environments);
	}
	
	public boolean isValid(NetworkEnvironment environment) {
		return environments.contains(environment);
	}
	
	public Set<NetworkEnvironment> getEnvironments() {
		return environments;
	}
}
