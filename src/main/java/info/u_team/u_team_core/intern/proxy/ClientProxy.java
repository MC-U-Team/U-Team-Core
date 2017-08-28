package info.u_team.u_team_core.intern.proxy;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.intern.client.Client;
import info.u_team.u_team_core.sub.metadata.MetadataFetcher;
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
		applyMetadata(event);
		setupClient();
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	// methods
	private void applyMetadata(FMLPreInitializationEvent event) {
		new MetadataFetcher(UCoreConstants.MODID).setName(UCoreConstants.NAME).setVersion(UCoreConstants.VERSION).applyMetadata(event.getModMetadata());
	}
	
	private void setupClient() {
		client = new Client();
	}
	
}
