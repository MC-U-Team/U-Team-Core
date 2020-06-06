package info.u_team.u_team_core.intern.event;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.discord.DiscordRichPresence;
import info.u_team.u_team_core.intern.discord.DiscordRichPresence.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = UCoreMain.MODID, value = Dist.CLIENT)
public class UpdateDiscordEventHandler {
	
	@SubscribeEvent
	public static void on(InitGuiEvent.Pre event) {
		if (!DiscordRichPresence.isEnabled()) {
			return;
		}
		if (event.getGui() instanceof MainMenuScreen || event.getGui() instanceof WorldSelectionScreen || event.getGui() instanceof MultiplayerScreen) {
			final State state = DiscordRichPresence.getCurrent();
			if (state == null || state.getState() != EnumState.MENU) {
				DiscordRichPresence.setIdling();
			}
		}
	}
	
	@SubscribeEvent
	public static void on(EntityJoinWorldEvent event) {
		if (!DiscordRichPresence.isEnabled()) {
			return;
		}
		if (event.getEntity() instanceof ClientPlayerEntity) {
			final ClientPlayerEntity player = (ClientPlayerEntity) event.getEntity();
			if (player.getUniqueID().equals(Minecraft.getInstance().player.getUniqueID())) {
				DiscordRichPresence.setDimension(player.getEntityWorld().dimension);
			}
		}
	}
}
