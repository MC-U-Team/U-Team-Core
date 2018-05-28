package info.u_team.u_team_core.intern.policy;

import java.awt.Dimension;
import java.io.IOException;
import java.net.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.HyperlinkEvent.EventType;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * Ensure you have accepted out eula. We did this to be on the safe side because of GDPR
 * 
 * @author HyCraftHD
 * @date 28.05.2018
 *
 */
public class EulaChecker {
	
	private final JDialog dialog;
	
	private ImageIcon icon;
	
	private JEditorPane jeditor;
	
	public EulaChecker() {
		dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		
		try {
			icon = new ImageIcon(ImageIO.read(Minecraft.getMinecraft().mcDefaultResourcePack.getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"))));
		} catch (Exception ex) {
			UCoreConstants.LOGGER.warn("Could not load image icon for policy checker because of: " + ex.getMessage());
		}
		
		try {
			jeditor = new JEditorPane(getClass().getResource("/assets/uteamcore/eula/eula.html"));
			jeditor.addHyperlinkListener(new HyperlinkListener() {
				
				@Override
				public void hyperlinkUpdate(HyperlinkEvent event) {
					if (event.getEventType() == EventType.ACTIVATED) {
						openLink(event.getURL());
					}
				}
			});
			jeditor.setEditable(false);
			jeditor.setPreferredSize(new Dimension(450, 500));
		} catch (IOException ex) {
			UCoreConstants.LOGGER.error("Could not setup eula window because of: " + ex.getMessage());
		}
		show();
	}
	
	public void show() {
		int value = showPolicy();
		if (value != 0) {
			value = showError();
			if (value == 0) {
				show();
			} else {
				UCoreConstants.LOGGER.fatal("Exit because you didn't accept the eula.");
				FMLCommonHandler.instance().exitJava(0, true);
			}
		}
		dialog.dispose();
	}
	
	private int showPolicy() {
		return JOptionPane.showConfirmDialog(dialog, new JScrollPane(jeditor), "U Team Core Eula", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
	}
	
	private int showError() {
		return JOptionPane.showConfirmDialog(dialog, I18n.format("client.privacy.decline").replace("[newline]", "\n"), "U Team Core Eula", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, icon);
	}
	
	private void openLink(URL url) {
		try {
			Class<?> oclass = Class.forName("java.awt.Desktop");
			Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
			oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { url.toURI() });
		} catch (Exception ex) {
			UCoreConstants.LOGGER.error("Could not open link because of: " + ex.getMessage());
		}
	}
}
