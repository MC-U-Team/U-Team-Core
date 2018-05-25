package info.u_team.u_team_core.intern.event;

import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
		if (event.gui instanceof GuiMainMenu || event.gui instanceof GuiSelectWorld || event.gui instanceof GuiMultiplayer) {
			DiscordRichPresence.setIdling();
		}
	}
	
	@SubscribeEvent
	public void on(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerSP) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (player.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) {
				DiscordRichPresence.setDimension(player.getEntityWorld().provider);
			}
		}
	}
}
