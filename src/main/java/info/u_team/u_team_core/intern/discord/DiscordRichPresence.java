package info.u_team.u_team_core.intern.discord;

import java.time.OffsetDateTime;
import java.util.*;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.IPCClient;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.entities.RichPresence.Builder;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
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
			UCoreMod.LOGGER.info("Discord client found and connected.");
		} catch (final NoDiscordClientException ex) {
			UCoreMod.LOGGER.info("Discord client was not found.");
		}
	}
	
	public static void stop() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
		try {
			CLIENT.close();
		} catch (final Exception ex) {
		}
		errorCount = 0;
		isEnabled = false;
		UCoreMod.LOGGER.info("Discord client closed.");
	}
	
	public static void setIdling() {
		setState(new State(EnumState.MENU));
	}
	
	public static void setDimension(World world) {
		setState(getStateFromDimension(world));
	}
	
	public static State getStateFromDimension(World world) {
		final ResourceLocation dimensionKey = world.func_234923_W_().func_240901_a_();
		if (dimensionKey.equals(World.field_234918_g_.func_240901_a_())) {
			return new State(EnumState.OVERWORLD);
		} else if (dimensionKey.equals(World.field_234919_h_.func_240901_a_())) {
			return new State(EnumState.NETHER);
		} else if (dimensionKey.equals(World.field_234920_i_.func_240901_a_())) {
			return new State(EnumState.END);
		} else {
			return new State(EnumState.DIM, dimensionKey.getPath());
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
					UCoreMod.LOGGER.info("Discord rich presence stopped cause connection is not working.");
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
