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
	
	private static Property client_trackclientdata, client_showeula;
	private static Property discord_enablerichpresence;
	
	public static void init(File file) {
		configuration = new Configuration(file);
		configuration.load();
		
		client_trackclientdata = configuration.get("client", "TrackClientData", true, "Track playtime, pc specifics and software like java, mc and forge version, as well as installed mods.");
		client_showeula = configuration.get("client", "EulaShow", true, "Show eula on startup.");
		
		discord_enablerichpresence = configuration.get("discord", "EnableRichPresence", true, "If you have discord installed, then it's cool to show some details about your game ;)");
		
		configuration.save();
	}
	
	public static boolean getTrackClientData() {
		return client_trackclientdata.getBoolean();
	}
	
	public static boolean getDiscordRichPresenceEnabled() {
		return discord_enablerichpresence.getBoolean();
	}
	
	public static boolean getEulaShow() {
		return client_showeula.getBoolean();
	}
	
	public static void setEulaShow(boolean value) {
		client_showeula.set(value);
		configuration.save();
	}
	
}
