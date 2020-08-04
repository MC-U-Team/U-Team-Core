package info.u_team.u_team_core.intern.discord;

import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.intern.discord.DiscordRichPresence.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.*;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class UpdateDiscordEventHandler {
	
	private static void onInitGuiPre(InitGuiEvent.Pre event) {
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
	
	private static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!DiscordRichPresence.isEnabled()) {
			return;
		}
		if (event.getEntity() instanceof ClientPlayerEntity) {
			final ClientPlayerEntity player = (ClientPlayerEntity) event.getEntity();
			if (player.getUniqueID().equals(Minecraft.getInstance().player.getUniqueID())) {
				DiscordRichPresence.setDimension(player.getEntityWorld());
			}
		}
	}
	
	private static void setup(FMLCommonSetupEvent event) {
		if (ClientConfig.getInstance().discordRichPresence.get()) {
			DiscordRichPresence.start();
		}
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(UpdateDiscordEventHandler::onInitGuiPre);
		bus.addListener(UpdateDiscordEventHandler::onEntityJoinWorld);
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(UpdateDiscordEventHandler::setup);
	}
}
