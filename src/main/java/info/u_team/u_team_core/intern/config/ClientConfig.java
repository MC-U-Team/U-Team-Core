package info.u_team.u_team_core.intern.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ClientConfig {
	
	public static final ForgeConfigSpec config;
	private static final ClientConfig instance;
	
	static {
		Pair<ClientConfig, ForgeConfigSpec> pair = new Builder().configure(ClientConfig::new);
		config = pair.getRight();
		instance = pair.getLeft();
	}
	
	public static ClientConfig getInstance() {
		return instance;
	}
	
	public final BooleanValue discordRichPresence;
	
	private ClientConfig(Builder builder) {
		builder.comment("Client configuration settings").push("client");
		discordRichPresence = builder.comment("If you have discord installed it will show your some details about your game as rich presence").translation("uteamcore:configgui.discordRichPresence").define("discordRichPresence", true);
		builder.pop();
	}
	
}
