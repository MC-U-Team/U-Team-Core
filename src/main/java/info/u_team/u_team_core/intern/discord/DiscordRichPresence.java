package info.u_team.u_team_core.intern.discord;

import java.time.OffsetDateTime;

import info.u_team.u_team_core.UCoreConstants;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.IPCClient;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.entities.RichPresence.Builder;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.Loader;

/**
 * Discord rich presence
 * 
 * @author HyCraftHD
 * @date 24.03.2018
 */
public class DiscordRichPresence {
	
	private static IPCClient client = new IPCClient(427196986064764928L);
	
	private static State current = null;
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				client.close();
			}
		});
		client.setListener(new DiscordRichPresenceListener());
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
		setState(State.MENU);
	}
	
	public static void setDimension(WorldProvider provider) {
		switch (provider.getDimension()) {
		case -1:
			setState(State.NETHER);
			break;
		case 0:
			setState(State.OVERWORLD);
			break;
		case 1:
			setState(State.END);
			break;
		default:
			setState(State.DIM, provider.getDimensionType().getName());
			break;
		}
	}
	
	public static void setState(State state) {
		setState(state, "");
	}
	
	public static void setState(State state, String replace) {
		if (state == current && (state != State.DIM && current != State.DIM)) {
			return;
		}
		current = state;
		try {
			Builder builder = new Builder();
			builder.setDetails("Minecraft " + UCoreConstants.MCVERSION + " with " + Loader.instance().getModList().size() + " Mods");
			builder.setState(state.getMessage(replace));
			builder.setStartTimestamp(OffsetDateTime.now());
			builder.setLargeImage(state.getImageKey(), state.getImageName(replace));
			if (state == State.MENU) {
				builder.setSmallImage("uteamcore", "U Team Core");
			}
			client.sendRichPresence(builder.build());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static enum State {
		MENU("Idling in menu", "Minecraft", "minecraft"),
		OVERWORLD("Dimension: Overworld", "Overworld", "world_overworld"),
		NETHER("Dimension: Nether", "Nether", "world_nether"),
		END("Dimension: The End", "The End", "world_the_end"),
		DIM("Dimension: %s", "%s", "world_dim");
		
		private String message, imagename, imagekey;
		
		private State(String message, String imagename, String imagekey) {
			this.message = message;
			this.imagename = imagename;
			this.imagekey = imagekey;
		}
		
		public String getMessage(String replace) {
			return message.replace("%s", replace);
		}
		
		public String getImageName(String replace) {
			return imagename.replace("%s", replace);
		}
		
		public String getImageKey() {
			return imagekey;
		}
		
	}
	
}
