package info.u_team.u_team_core.intern.discord;

import java.time.OffsetDateTime;
import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.event.EventHandlerUpdateDiscordRichPresence;
import info.u_team.u_team_core.registry.util.CommonRegistry;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.IPCClient;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.entities.RichPresence.Builder;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import net.minecraft.world.dimension.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.versions.mcp.MCPVersion;

@OnlyIn(Dist.CLIENT)
public class DiscordRichPresence {
	
	private static IPCClient client = new IPCClient(427196986064764928L);
	
	private static boolean isEnabled = false;
	
	private static OffsetDateTime time = OffsetDateTime.now();
	public static State current = new State(EnumState.STARTUP);
	
	private static int errorcount = 0;
	
	private static Timer timer = new Timer("Discord Rich Presence Timer Thread");
	
	private static TimerTask task;
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> stop(), "Discord Rich Presence Stop Thread"));
	}
	
	public static void start() {
		try {
			client.connect();
			timer.schedule(task = new TimerTask() {
				
				@Override
				public void run() {
					setState(current);
				}
			}, 1000, 1000 * 120);
			CommonRegistry.registerEventHandler(EventHandlerUpdateDiscordRichPresence.class);
			isEnabled = true;
			UCoreMain.logger.info("Discord client found and connected.");
		} catch (NoDiscordClientException ex) {
			UCoreMain.logger.info("Discord client was not found.");
		}
	}
	
	public static void stop() {
		if (task != null) {
			task.cancel();
			task = null;
		}
		try {
			client.close();
		} catch (Exception ex) {
		}
		errorcount = 0;
		CommonRegistry.unregisterEventHandler(EventHandlerUpdateDiscordRichPresence.class);
		isEnabled = false;
		UCoreMain.logger.info("Discord client closed.");
	}
	
	public static void setIdling() {
		setState(new State(EnumState.MENU));
	}
	
	public static void setDimension(Dimension dimension) {
		setState(getStateFromDimension(dimension));
	}
	
	public static State getStateFromDimension(Dimension dimension) {
		switch (dimension.getType().getId()) {
		case -1:
			return new State(EnumState.NETHER);
		case 0:
			return new State(EnumState.OVERWORLD);
		case 1:
			return new State(EnumState.END);
		default:
			return new State(EnumState.DIM, DimensionType.func_212678_a(dimension.getType()).getPath());
		}
	}
	
	public static void setState(State state) {
		current = state;
		Builder builder = new Builder();
		builder.setDetails(MCPVersion.getMCVersion() + " with " + ModList.get().size() + " Mods");
		builder.setState(state.getState().getMessage(state.getReplace()));
		builder.setStartTimestamp(time);
		builder.setLargeImage(state.getState().getImageKey(), state.getState().getImageName(state.getReplace()));
		if (state.getState() == EnumState.MENU || state.getState() == EnumState.STARTUP) {
			builder.setSmallImage("uteamcore", "U-Team Core");
		}
		try {
			client.sendRichPresence(builder.build());
		} catch (Exception ex) {
			try {
				client.connect();
				errorcount = 0;
				client.sendRichPresence(builder.build());
			} catch (Exception ex2) {
				try {
					client.close();
				} catch (Exception ex3) {
				}
				errorcount++;
				if (errorcount > 10) {
					UCoreMain.logger.info("Discord rich presence stopped cause connection is not working.");
					stop();
				}
			}
		}
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
	
	public static State getCurrent() {
		return current;
	}
	
	public static class State {
		
		private EnumState state;
		private String replace;
		
		public State(EnumState state) {
			this(state, "");
		}
		
		public State(EnumState state, String replace) {
			this.state = state;
			this.replace = replace;
		}
		
		public EnumState getState() {
			return state;
		}
		
		public String getReplace() {
			return replace;
		}
	}
	
	public static enum EnumState {
		STARTUP("Starting Minecraft", "Minecraft", "minecraft"),
		MENU("Idling in menu", "Minecraft", "minecraft"),
		OVERWORLD("Dimension: Overworld", "Overworld", "world_overworld"),
		NETHER("Dimension: Nether", "Nether", "world_nether"),
		END("Dimension: The End", "The End", "world_the_end"),
		DIM("Dimension: %s", "%s", "world_dim");
		
		private String message, imagename, imagekey;
		
		private EnumState(String message, String imagename, String imagekey) {
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
