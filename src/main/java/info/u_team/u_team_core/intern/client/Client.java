package info.u_team.u_team_core.intern.client;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Client {
	
	private final String connectionurl = "http://localhost/minecraftauth/index.php"; // only debug now
	
	private Session session;
	private MinecraftSessionService sessionservice;
	
	private String servertoken;
	private String postrequest;
	
	public Client() {
		try {
			connectAndAuth();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void connectAndAuth() throws ClientAuthentificationException {
		setup();
		auth();
	}
	
	private void setup() {
		byte[] token = new byte[20];
		new Random().nextBytes(token);
		servertoken = new BigInteger(1, token).toString(16);
		
		Minecraft minecraft = Minecraft.getMinecraft();
		session = minecraft.getSession();
		sessionservice = minecraft.getSessionService();
		
		postrequest = "username=" + session.getUsername() + "&token=" + servertoken;
	}
	
	private void auth() throws ClientAuthentificationException {
		try {
			sessionservice.joinServer(session.getProfile(), session.getToken(), servertoken);
			
			HttpURLConnection connection = (HttpURLConnection) new URL(connectionurl).openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			OutputStream out = connection.getOutputStream();
			out.write(postrequest.getBytes(StandardCharsets.UTF_8));
			out.flush();
			
			if (connection.getResponseCode() != 200) {
				throw new IOException("Http Response: " + connection.getResponseCode() + " " + connection.getResponseMessage());
			}
		} catch (Exception ex) {
			throw new ClientAuthentificationException("Client authentification failed.", ex);
		}
	}
}
