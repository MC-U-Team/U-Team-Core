package info.u_team.u_team_core.intern.client.request;

import java.io.IOException;

/**
 * This class defines an Exception when the http server responds with error code
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class ClientResponseCodeException extends IOException {
	
	private static final long serialVersionUID = 1L;
	
	private int responecode;
	private String responemessage;
	
	public ClientResponseCodeException(int responsecode, String responsemessage) {
		super("Http Response: " + responsecode + " " + responsemessage);
		this.responecode = responsecode;
		this.responemessage = responsemessage;
	}
	
	public int getResponeCode() {
		return responecode;
	}
	
	public String getResponeMessage() {
		return responemessage;
	}
	
}
