package info.u_team.u_team_core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import info.u_team.u_team_core.internal.UpdateResolver;
import net.fabricmc.api.ModInitializer;

public class UCoreMod implements ModInitializer {
	
	public static final String MODID = "uteamcore";
	public static final Logger LOGGER = LogUtils.getLogger();
	
	@Override
	public void onInitialize() {
		UpdateResolver.load();
	}
}
