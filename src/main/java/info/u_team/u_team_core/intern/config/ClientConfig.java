package info.u_team.u_team_core.intern.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

public class ClientConfig {
	
	public static final ForgeConfigSpec CONFIG;
	private static final ClientConfig INSTANCE;
	
	static {
		final Pair<ClientConfig, ForgeConfigSpec> pair = new Builder().configure(ClientConfig::new);
		CONFIG = pair.getRight();
		INSTANCE = pair.getLeft();
	}
	
	public static ClientConfig getInstance() {
		return INSTANCE;
	}
	
	public final BooleanValue discordRichPresence;
	
	private ClientConfig(Builder builder) {
		builder.comment("Client configuration settings").push("client");
		discordRichPresence = builder.comment("If you have discord installed it will show your some details about your game as rich presence").define("discordRichPresence", true);
		builder.pop();
	}
	
}
