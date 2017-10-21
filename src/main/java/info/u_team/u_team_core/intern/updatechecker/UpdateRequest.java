package info.u_team.u_team_core.intern.updatechecker;

import java.io.*;
import java.net.*;

import com.google.common.io.ByteStreams;

/**
 * Update API<br>
 * -> Update request
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 *
 */
public class UpdateRequest {
	
	private String url;
	
	public UpdateRequest(String url) {
		this.url = url;
	}
	
	public String getData() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", "U-Team-Core Updatechecker");
		connection.setConnectTimeout(2000);
		connection.setReadTimeout(2000);
		
		InputStream in = connection.getInputStream();
		return new String(ByteStreams.toByteArray(in));
	}
	
}
