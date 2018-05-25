package info.u_team.u_team_core.intern.discord;

import java.util.*;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.*;
import info.u_team.u_team_core.repack.com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import info.u_team.u_team_core.repack.org.json.JSONObject;

/**
 * When discord connection is disrupted we are trying to reconnect with this listener
 * 
 * @author HyCraftHD
 * @date 25.05.2018
 *
 */
public class DiscordRichPresenceListener implements IPCListener {
	
	private Timer timer;
	private int reconnect;
	
	public DiscordRichPresenceListener() {
		timer = new Timer();
	}
	
	@Override
	public void onClose(IPCClient client, JSONObject json) {
		UCoreConstants.LOGGER.info("Discord connection was closed.");
	}
	
	@Override
	public void onDisconnect(IPCClient client, Throwable t) {
		UCoreConstants.LOGGER.info("Discord connection has been lost because of " + t.getMessage() + ".");
		addReconnectionTry(client);
	}
	
	@Override
	public void onReady(IPCClient client) {
		reconnect = 0;
	}
	
	private void addReconnectionTry(IPCClient client) {
		if (reconnect > 10) {
			return;
		}
		reconnect++;
		UCoreConstants.LOGGER.info("Trying to connect to discord again in 60 seconds.");
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					client.connect();
					UCoreConstants.LOGGER.info("Sucessfully reconnected to discord.");
				} catch (NoDiscordClientException ex) {
					UCoreConstants.LOGGER.info("Discord connection could not be recovered. Connection attempt " + reconnect + ". Retry in 60 seconds.");
					addReconnectionTry(client);
				}
			}
		}, 1000 * 60);
	}
	
}
