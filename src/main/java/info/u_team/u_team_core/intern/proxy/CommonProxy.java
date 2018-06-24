package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.registry.*;
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
		CommonRegistry.registerEventHandler(BlockRegistry.class);
		CommonRegistry.registerEventHandler(ItemRegistry.class);
		CommonRegistry.registerEventHandler(EntityRegistry.class);
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
	public void serverstart(FMLServerStartingEvent event) {
	}
	
}
