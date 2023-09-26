package info.u_team.u_team_core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

public final class UCoreReference {
	
	public static final String MODID = "uteamcore";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	public static final int PROTOCOL_VERSION = 0x0;
	
	private UCoreReference() {
	}
}
