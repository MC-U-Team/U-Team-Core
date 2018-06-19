package info.u_team.u_team_core.intern.config;

import java.io.File;

import net.minecraftforge.common.config.*;

/**
 * This class manages our config file
 * 
 * @author HyCraftHD
 * @date 28.05.2018
 *
 */
public class Config {
	
	private static Configuration configuration;
	
	private static Property discord_enablerichpresence;
	
	public static void init(File file) {
		configuration = new Configuration(file);
		configuration.load();
		
		discord_enablerichpresence = configuration.get("discord", "EnableRichPresence", true, "If you have discord installed, then it's cool to show some details about your game ;)");
		
		configuration.save();
	}
	
	public static boolean getDiscordRichPresenceEnabled() {
		return discord_enablerichpresence.getBoolean();
	}
	
}