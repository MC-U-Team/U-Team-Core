package info.u_team.u_team_core.intern.discord;

import com.mojang.blaze3d.matrix.MatrixStack;

import info.u_team.u_team_core.gui.elements.*;
import info.u_team.u_team_core.intern.config.ClientConfig;
import info.u_team.u_team_core.util.RGBA;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.*;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class DiscordConfigScreen extends Screen {
	
	private final Screen screenBefore;
	
	public DiscordConfigScreen(Screen screenBefore) {
		super(new TranslationTextComponent("screen.uteamcore.config.discord.title"));
		this.screenBefore = screenBefore;
	}
	
	@Override
	protected void init() {
		final BooleanValue discordRichPresence = ClientConfig.getInstance().discordRichPresence;
		
		final ITextComponent on = new TranslationTextComponent("screen.uteamcore.config.discord.on");
		final ITextComponent off = new TranslationTextComponent("screen.uteamcore.config.discord.off");
		
		final ScalableActivatableButton toggleDiscordRichPresenceButton = addButton(new ScalableActivatableButton(width / 2 - 100, 50, 200, 20, discordRichPresence.get() ? on : off, 1, discordRichPresence.get(), new RGBA(0x006442FF)));
		toggleDiscordRichPresenceButton.setPressable(() -> {
			discordRichPresence.set(!discordRichPresence.get());
			
			if (discordRichPresence.get() && !DiscordRichPresence.isEnabled()) {
				DiscordRichPresence.start();
				if (minecraft.world == null) {
					DiscordRichPresence.setIdling();
				} else {
					DiscordRichPresence.setDimension(minecraft.world);
				}
			} else if (!discordRichPresence.get() && DiscordRichPresence.isEnabled()) {
				DiscordRichPresence.stop();
			}
			
			toggleDiscordRichPresenceButton.setActivated(discordRichPresence.get());
			toggleDiscordRichPresenceButton.setMessage(discordRichPresence.get() ? on : off);
		});
		
		addButton(new UButton(width / 2 - 100, 80, 200, 20, new TranslationTextComponent("screen.uteamcore.config.discord.done"), button -> closeScreen()));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(matrixStack);
		drawCenteredString(matrixStack, font, title, width / 2, 15, 0xFFFFFF);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		buttons.forEach(widget -> widget.renderToolTip(matrixStack, mouseX, mouseY));
	}
	
	@Override
	public void closeScreen() {
		minecraft.displayGuiScreen(screenBefore);
	}
	
}
