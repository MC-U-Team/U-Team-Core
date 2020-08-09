package info.u_team.u_team_core.intern.discord;

import java.time.OffsetDateTime;
import java.util.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.IPCClient;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.entities.RichPresence.Builder;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import net.minecraft.world.dimension.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.versions.mcp.MCPVersion;

@OnlyIn(Dist.CLIENT)
public class DiscordRichPresence {
	
	private static final IPCClient CLIENT = new IPCClient(427196986064764928L);
	
	private static boolean isEnabled = false;
	
	private static final OffsetDateTime TIME = OffsetDateTime.now();
	public static State currentState = new State(EnumState.STARTUP);
	
	private static int errorCount = 0;
	
	private static final Timer TIMER = new Timer("Discord Rich Presence Timer Thread");
	private static TimerTask timerTask;
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> stop(), "Discord Rich Presence Stop Thread"));
	}
	
	public static void start() {
		try {
			CLIENT.connect();
			TIMER.schedule(timerTask = new TimerTask() {
				
				@Override
				public void run() {
					setState(currentState);
				}
			}, 1000, 1000 * 120);
			isEnabled = true;
			UCoreMain.LOGGER.info("Discord client found and connected.");
		} catch (final NoDiscordClientException ex) {
			UCoreMain.LOGGER.info("Discord client was not found.");
		}
	}
	
	public static void stop() {
		boolean wasConnected = false;
		if (timerTask != null) {
			wasConnected = true;
			timerTask.cancel();
			timerTask = null;
		}
		try {
			CLIENT.close();
		} catch (final Exception ex) {
		}
		errorCount = 0;
		isEnabled = false;
		if (wasConnected) {
			UCoreMain.LOGGER.info("Discord client closed.");
		}
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
			return new State(EnumState.DIM, DimensionType.getKey(dimension.getType()).getPath());
		}
	}
	
	public static void setState(State state) {
		currentState = state;
		final Builder builder = new Builder();
		builder.setDetails(MCPVersion.getMCVersion() + " with " + ModList.get().size() + " Mods");
		builder.setState(state.getState().getMessage(state.getReplace()));
		builder.setStartTimestamp(TIME);
		builder.setLargeImage(state.getState().getImageKey(), state.getState().getImageName(state.getReplace()));
		if (state.getState() == EnumState.MENU || state.getState() == EnumState.STARTUP) {
			builder.setSmallImage("uteamcore", "U-Team Core");
		}
		try {
			CLIENT.sendRichPresence(builder.build());
		} catch (final Exception ex) {
			try {
				CLIENT.connect();
				errorCount = 0;
				CLIENT.sendRichPresence(builder.build());
			} catch (final Exception ex2) {
				try {
					CLIENT.close();
				} catch (final Exception ex3) {
				}
				errorCount++;
				if (errorCount > 10) {
					UCoreMain.LOGGER.info("Discord rich presence stopped cause connection is not working.");
					stop();
				}
			}
		}
	}
	
	public static boolean isEnabled() {
		return isEnabled;
	}
	
	public static State getCurrent() {
		return currentState;
	}
	
	public static class State {
		
		private final EnumState state;
		private final String replace;
		
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
		
		private final String message, imagename, imagekey;
		
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
