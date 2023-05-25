package info.u_team.u_team_core.api;

import info.u_team.u_team_core.util.ServiceUtil;

public interface Platform {
	
	Platform INSTANCE = ServiceUtil.loadOne(Platform.class);
	
	static Platform getInstance() {
		return INSTANCE;
	}
	
	Environment getEnvironment();
	
	static enum Environment {
		CLIENT,
		SERVER
	}
}
