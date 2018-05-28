package info.u_team.u_team_core.intern.client.request;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Construct a request to server
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class Request {
	
	private static final String connectionurl = "https://api.u-team.info/client/client";
	
	private String postrequest;
	
	public Request(RequestMode requestmode, String post) {
		postrequest = "mode=" + requestmode.getMode() + post;
	}
	
	public HttpURLConnection create() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(connectionurl).openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", "U-Team-Core Client");
		connection.setDoOutput(true);
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		
		OutputStream out = connection.getOutputStream();
		out.write(postrequest.getBytes(StandardCharsets.UTF_8));
		out.flush();
		
		if (connection.getResponseCode() != 200) {
			throw new ClientResponseCodeException(connection.getResponseCode(), connection.getResponseMessage());
		}
		
		return connection;
	}
	
}
