package info.u_team.u_team_core.intern.client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.*;
import java.util.*;

import info.u_team.u_team_core.intern.client.request.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Client {
	
	private boolean auth = false;
	
	public Client() {
		CookieHandler.setDefault(new CookieManager()); // We need this to keep the PHPSESSION cookie
		System.setProperty("http.agent", "Chrome"); // We need this to let cloudflare accept out request
	}
	
	// public send data methods
	
	public void firstSend(Map<String, String> map) {
		send(RequestMode.FIRSTSEND, map);
	}
	
	public void keepAlive() {
		send(RequestMode.KEEP_ALIVE, new HashMap<>());
	}
	
	// private utility and authentication methods
	
	private void send(RequestMode mode, Map<String, String> map) {
		try {
			if (isauth()) {
				Scanner scanner = new Scanner(createPostRequest(mode, map).getInputStream());
				while (scanner.hasNextLine()) {
					System.out.println(scanner.nextLine());
				}
				scanner.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			checkauth(ex);
		}
	}
	
	private void checkauth(IOException ex) {
		if (ex instanceof ClientResponseCodeException && ((ClientResponseCodeException) ex).getResponeCode() == 401) {
			auth = false;
		}
	}
	
	private boolean isauth() {
		try {
			if (!auth) {
				auth();
			}
		} catch (ClientAuthentificationException ex) {
			ex.printStackTrace();
		}
		return auth;
	}
	
	private void auth() throws ClientAuthentificationException {
		try {
			byte[] token = new byte[20];
			new Random().nextBytes(token);
			String servertoken = new BigInteger(1, token).toString(16);
			
			Minecraft minecraft = Minecraft.getMinecraft();
			Session session = minecraft.getSession();
			
			minecraft.getSessionService().joinServer(session.getProfile(), session.getToken(), servertoken);
			
			Map<String, String> hashmap = new HashMap<>();
			hashmap.put("username", session.getUsername());
			hashmap.put("token", servertoken);
			
			Scanner scanner = new Scanner(createPostRequest(RequestMode.AUTH, hashmap).getInputStream());
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();
			auth = true;
		} catch (Exception ex) {
			throw new ClientAuthentificationException("Client authentification failed.", ex);
		}
	}
	
	private HttpURLConnection createPostRequest(RequestMode mode, Map<String, String> posts) throws IOException {
		StringBuilder builder = new StringBuilder();
		posts.forEach((key, value) -> builder.append("&" + key + "=" + value));
		return new Request(mode, builder.toString()).create();
	}
}
