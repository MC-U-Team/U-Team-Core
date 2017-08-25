package info.u_team.u_team_core.intern.client.request;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Request {
	
	private static final String connectionurl = "http://localhost/minecraftauth/index.php"; // debug url now (will be https)
	
	private String postrequest;
	
	public Request(RequestMode requestmode, String post) {
		postrequest = "mode=" + requestmode.getMode() + post;
	}
	
	public HttpURLConnection create() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(connectionurl).openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		
		OutputStream out = connection.getOutputStream();
		out.write(postrequest.getBytes(StandardCharsets.UTF_8));
		out.flush();
		
		if (connection.getResponseCode() != 200) {
			throw new IOException("Http Response: " + connection.getResponseCode() + " " + connection.getResponseMessage());
		}
		
		return connection;
	}
	
}
