package info.u_team.u_team_core.intern.discord;

import java.time.OffsetDateTime;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.IPCClient;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.entities.RichPresence;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;

public class DiscordRichPresence {
	
	private static IPCClient client = new IPCClient(427196986064764928L);
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				client.close();
			}
		});
	}
	
	public static void start() {
		try {
			client.connect();
		} catch (NoDiscordClientException ex) {
			UCoreConstants.LOGGER.info("No discord client is present.");
		}
		setIdling();
	}
	
	public static void setIdling() {
		setState("Idling in menue");
	}
	
	public static void setWorld(String world) {
		setState("World: " + world);
	}
	
	public static void setServer(String server) {
		setState("Server: " + server);
	}
	
	public static void setState(String state) {
		try {
			RichPresence.Builder builder = new RichPresence.Builder();
			builder.setDetails("Playing Minecraft " + UCoreConstants.MCVERSION).setState(state).setStartTimestamp(OffsetDateTime.now()).setLargeImage("minecraft", "Minecraft");
			client.sendRichPresence(builder.build());
		} catch (Exception ex) {
		}
	}
}
