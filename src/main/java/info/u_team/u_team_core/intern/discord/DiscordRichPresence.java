/*-*****************************************************************************
 * Copyright 2018 U-Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package info.u_team.u_team_core.intern.discord;

import java.time.OffsetDateTime;
import java.util.*;

import info.u_team.u_team_core.UCoreConstants;
import info.u_team.u_team_core.intern.event.EventHandlerUpdateDiscordRichPresence;
import info.u_team.u_team_core.registry.CommonRegistry;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.IPCClient;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.entities.RichPresence.Builder;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.Loader;

/**
 * Using ipc client to connect with discord and using it's rich presence. This
 * class handles auto reconnection and failures
 * 
 * @author HyCraftHD
 * @date 08.09.2018
 */
public class DiscordRichPresence {
	
	private static IPCClient client = new IPCClient(427196986064764928L);
	
	private static boolean isEnabled = false;
	
	private static OffsetDateTime time = OffsetDateTime.now();
	public static State current = new State(EnumState.STARTUP);
	
	private static int errorcount = 0;
	
	private static Timer timer = new Timer("Discord Rich Presence Timer Thread");
	
	private static TimerTask task;
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			stop();
		}));
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
			UCoreConstants.LOGGER.info("Discord client found and connected.");
		} catch (NoDiscordClientException ex) {
			UCoreConstants.LOGGER.info("Discord client was not found.");
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
		UCoreConstants.LOGGER.info("Discord client closed.");
	}
	
	public static void setIdling() {
		setState(new State(EnumState.MENU));
	}
	
	public static void setDimension(WorldProvider provider) {
		setState(getStateFromDimension(provider));
	}
	
	public static State getStateFromDimension(WorldProvider provider) {
		switch (provider.getDimension()) {
		case -1:
			return new State(EnumState.NETHER);
		case 0:
			return new State(EnumState.OVERWORLD);
		case 1:
			return new State(EnumState.END);
		default:
			return new State(EnumState.DIM, provider.getDimensionType().getName());
		}
	}
	
	public static void setState(State state) {
		current = state;
		Builder builder = new Builder();
		builder.setDetails(UCoreConstants.MCVERSION + " with " + Loader.instance().getModList().size() + " Mods");
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
					UCoreConstants.LOGGER.info("Discord rich presence stopped cause connection is not working.");
					stop();
				}
			}
		}
	}
	
	public static boolean isEnabled() {
		return isEnabled;
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
