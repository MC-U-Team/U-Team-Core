package info.u_team.u_team_core.intern.client;

public class ClientAuthentificationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ClientAuthentificationException() {
	}
	
	public ClientAuthentificationException(String message) {
		super(message);
	}
	
	public ClientAuthentificationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ClientAuthentificationException(Throwable cause) {
		super(cause);
	}
	
}
