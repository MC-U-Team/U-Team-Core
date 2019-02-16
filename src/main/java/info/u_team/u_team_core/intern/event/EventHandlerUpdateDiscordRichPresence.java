package info.u_team.u_team_core.intern.event;

import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class EventHandlerUpdateDiscordRichPresence {
	
	@SubscribeEvent
	public static void on(InitGuiEvent.Pre event) {
		if (event.getGui() instanceof GuiMainMenu || event.getGui() instanceof GuiWorldSelection || event.getGui() instanceof GuiMultiplayer) {
			DiscordRichPresence.setIdling();
		}
	}
	
	@SubscribeEvent
	public static void on(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayerSP) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (player.getUniqueID().equals(Minecraft.getInstance().player.getUniqueID())) {
				DiscordRichPresence.setDimension(player.getEntityWorld().dimension);
			}
		}
	}
}
