package info.u_team.u_team_core.intern.client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.util.*;

import org.apache.commons.codec.binary.Base64;

import com.google.common.base.Charsets;
import com.google.gson.*;

import info.u_team.u_team_core.intern.client.request.*;
import io.netty.handler.codec.base64.Base64Decoder;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Client {
	
	public Client() {
		try {
			auth();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			System.out.println("......................................................................................");
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
//				Gson gson = new GsonBuilder().setPrettyPrinting().create();
//				
//				JsonElement element = new JsonParser().parse(scanner.nextLine());
//				
//				System.out.println(gson.toJson(element));
//				
//				String value = element.getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();
//				
//				System.out.println(value);
//				
//				System.out.println(new String(Base64.decodeBase64(value), Charsets.UTF_8));
				
			}
			
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
