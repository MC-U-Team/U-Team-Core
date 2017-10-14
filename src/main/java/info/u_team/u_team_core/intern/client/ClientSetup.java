package info.u_team.u_team_core.intern.client;

import java.util.HashMap;

public class ClientSetup {
	
	private Client client;
	
	public ClientSetup() {
		client = new Client();
		firstsend();
	}
	
	private void firstsend() {
		HashMap<String, String> map = new HashMap<>();
		map.put("lol", "test");
		client.firstsend(map);
	}
	
}
