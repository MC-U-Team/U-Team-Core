package info.u_team.u_team_core.intern.client.request;

/**
 * Different request modes
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public enum RequestMode {
	
	AUTH("auth"),
	FIRSTSEND("firstsend"),
	KEEP_ALIVE("keep-alive");
	
	private String mode;
	
	private RequestMode(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
}
