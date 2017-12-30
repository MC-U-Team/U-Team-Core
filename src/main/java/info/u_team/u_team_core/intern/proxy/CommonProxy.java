package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.intern.command.CommandUSchematic;
import info.u_team.u_team_core.intern.generation.WorldGenerator;
import net.minecraftforge.fml.common.event.*;

/**
 * This class has methods that are only run by the server.
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 *
 */
public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		System.setProperty("http.agent", "Chrome"); // We need this to let cloudflare accept our requests
	}
	
	public void init(FMLInitializationEvent event) {
		new WorldGenerator();
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
	public void serverstart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandUSchematic());
	}
	
}
