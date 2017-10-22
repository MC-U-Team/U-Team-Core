package info.u_team.u_team_core.intern.proxy;

import javax.swing.*;

import info.u_team.u_team_core.intern.UCoreConstants;
import info.u_team.u_team_core.intern.client.ClientSetup;
import info.u_team.u_team_core.intern.config.Config;
import info.u_team.u_team_core.intern.event.UpdateClientListener;
import info.u_team.u_team_core.sub.metadata.MetadataFetcher;
import info.u_team.u_team_core.updatechecker.UpdateCheckerRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
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
	
	public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		Config.init(event.getSuggestedConfigurationFile());
		new MetadataFetcher(UCoreConstants.MODID).setName(UCoreConstants.NAME).setVersion(UCoreConstants.VERSION).applyMetadata(event.getModMetadata());
		UpdateCheckerRegistry.addMod(UCoreConstants.MODID, "https://api.u-team.info/update/uteamcore.json");
		
		if (Config.getPrivacyPolicyShow()) {
			JOptionPane.showMessageDialog(null, I18n.format("client.privacy").replace("[newline]", "\n"), "Privacy Policy", JOptionPane.INFORMATION_MESSAGE);
			Config.setPrivacyPolicyShow(false);
		}
		if (Config.getTrackClientData()) {
			new ClientSetup();
		}
	}
	
	public void init(FMLInitializationEvent event) {
		super.init(event);
		UpdateCheckerRegistry.checker.start();
		MinecraftForge.EVENT_BUS.register(new UpdateClientListener());
	}
	
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
	
	public void serverstart(FMLServerStartingEvent event) {
		super.serverstart(event);
	}
	
}
