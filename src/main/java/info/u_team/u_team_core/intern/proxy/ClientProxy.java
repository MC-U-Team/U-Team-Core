package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.intern.client.Client;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * This class has methods that are only run by the client. To run common things you need to run super method of {@link CommonProxy}
 * 
 * @author HyCraftHD
 * @date 16.08.2017
 *
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	public static Client client;
	
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		setupClient();
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	private void setupClient() {
		client = new Client();
	}
	
}
