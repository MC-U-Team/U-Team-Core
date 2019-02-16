package info.u_team.u_team_core.registry;

import net.minecraftforge.common.MinecraftForge;

public class CommonRegistry {
	
	public static void registerEventHandler(Object object) {
		MinecraftForge.EVENT_BUS.register(object);
	}
	
	public static void unregisterEventHandler(Object object) {
		MinecraftForge.EVENT_BUS.unregister(object);
	}
	
	public static void registerEventHandler(Object... objects) {
		for (Object object : objects) {
			MinecraftForge.EVENT_BUS.register(object);
		}
	}
	
	public static void unregisterEventHandler(Object... objects) {
		for (Object object : objects) {
			MinecraftForge.EVENT_BUS.unregister(object);
		}
	}
	
}
