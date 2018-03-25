package info.u_team.u_team_core.intern.event;

import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

/**
 * Internal Event for update discord rich presence
 * 
 * @author HyCraftHD
 * @date 24.03.2018
 *
 */
@SideOnly(Side.CLIENT)
public class UpdateDiscordRichPresenceEvent {
	
	@SubscribeEvent
	public void on(InitGuiEvent.Pre event) {
		try {
			if (event.gui instanceof GuiMainMenu || event.gui instanceof GuiSelectWorld || event.gui instanceof GuiMultiplayer) {
				DiscordRichPresence.setIdling();
			}
		} catch (Exception ex) {
		}
	}
	
	@SubscribeEvent
	public void on(EntityJoinWorldEvent event) {
		try {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				if (player.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) {
					if (Minecraft.getMinecraft().getCurrentServerData() != null) {
						DiscordRichPresence.setServer(Minecraft.getMinecraft().getCurrentServerData().serverName);
					} else {
						if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
							DiscordRichPresence.setWorld(Minecraft.getMinecraft().getIntegratedServer().getWorldName());
						}
					}
				}
			}
		} catch (Exception ex) {
		}
	}
}
