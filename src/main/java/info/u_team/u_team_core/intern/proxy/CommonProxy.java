package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.intern.config.ClientConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;

public class CommonProxy {
	
	public static void construct() {
		ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.config);
	}
	
	public static void setup() {
	}
	
}
