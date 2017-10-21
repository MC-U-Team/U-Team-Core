package info.u_team.u_team_core.intern.client;

/**
 * This class defines an Exception when the player could not be authenticated
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class ClientAuthenticationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ClientAuthenticationException() {
	}
	
	public ClientAuthenticationException(String message) {
		super(message);
	}
	
	public ClientAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ClientAuthenticationException(Throwable cause) {
		super(cause);
	}
	
}
