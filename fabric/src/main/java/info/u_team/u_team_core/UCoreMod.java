package info.u_team.u_team_core;

import org.slf4j.Logger;

import info.u_team.u_team_core.intern.UpdateResolver;
import net.fabricmc.api.ModInitializer;

public class UCoreMod implements ModInitializer {
	
	public static final String MODID = UCoreReference.MODID;
	public static final Logger LOGGER = UCoreReference.LOGGER;
	
	@Override
	public void onInitialize() {
		UpdateResolver.load();
	}
}
