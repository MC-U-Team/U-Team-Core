package info.u_team.u_team_core;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.intern.proxy.CommonProxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.*;

/**
 * This is the main class of this utility mod and represents the mod in forge. It is used to listen to events and setup everything nessesary.
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 *
 */
@Mod(modid = UCoreConstants.MODID, name = UCoreConstants.NAME, version = UCoreConstants.VERSION, acceptedMinecraftVersions = UCoreConstants.MCVERSION)
public class UCoreMain {
	
	// instance setup and other variables
	
	private UCoreMain() { // Let this class only have one instance (singleton)
	}
	
	private static UCoreMain instance = new UCoreMain();
	
	@SidedProxy(serverSide = UCoreConstants.COMMONPROXY, clientSide = UCoreConstants.CLIENTPROXY)
	private static CommonProxy proxy;
	
	public static String SUBMOD_MODID = UCoreConstants.MODID;
	
	// getter and setter
	
	@InstanceFactory
	public static UCoreMain getInstance() {
		return instance;
	}
	
	public CommonProxy getProxy() {
		return proxy;
	}
	
	// fml events
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		proxy.preinit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
	
	@EventHandler
	public void postinit(FMLPostInitializationEvent event) {
		proxy.postinit();
	}
}
