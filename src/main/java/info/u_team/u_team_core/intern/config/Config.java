package info.u_team.u_team_core.intern.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
	
	private static Configuration configuration;
	
	public static void init(File file) {
		configuration = new Configuration(file);
		configuration.load();
	}
	
	public static boolean getTrackClientData() {
		boolean value = configuration.get("client", "TrackClientData", true, "Track playtime, pc specifics and software like java, mc and forge version, as well as installed mods.").getBoolean();
		configuration.save();
		return value;
	}
	
	public static boolean getDiscordRichPresenceEnabled() {
		boolean value = configuration.get("discord", "EnableRichPresence", true, "If you have discord installed, then it's cool to show some details about your game ;)").getBoolean();
		configuration.save();
		return value;
	}
	
	public static boolean getPrivacyPolicyShow() {
		boolean value = configuration.get("client", "PrivacyPolicyShow", true, "Show privacy policy on startup.").getBoolean();
		configuration.save();
		return value;
	}
	
	public static void setPrivacyPolicyShow(boolean value) {
		configuration.get("client", "PrivacyPolicyShow", true, "Show privacy policy on startup.").set(value);
		configuration.save();
	}
}
