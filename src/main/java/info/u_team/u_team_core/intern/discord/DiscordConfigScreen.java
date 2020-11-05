package info.u_team.u_team_core.intern.discord;

import info.u_team.u_team_core.gui.elements.ActiveButton;
import info.u_team.u_team_core.intern.config.ClientConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class DiscordConfigScreen extends Screen {
	
	private final Screen screenBefore;
	
	public DiscordConfigScreen(Screen screenBefore) {
		super(ITextComponent.getTextComponentOrEmpty("discord"));
		this.screenBefore = screenBefore;
	}
	
	@Override
	protected void init() {
		final BooleanValue discordRichPresence = ClientConfig.getInstance().discordRichPresence;
		
		final ITextComponent on = ITextComponent.getTextComponentOrEmpty("Discord connection is on");
		final ITextComponent off = ITextComponent.getTextComponentOrEmpty("Discord connection is off");
		
		final ActiveButton button = addButton(new ActiveButton(30, 50, 200, 20, discordRichPresence.get() ? on : off, 0x006442FF));
		button.setActive(discordRichPresence.get());
		button.setPressable(() -> {
			discordRichPresence.set(!discordRichPresence.get());
			button.setActive(discordRichPresence.get());
			button.setMessage(discordRichPresence.get() ? on : off);
		});
	}
	
	@Override
	public void closeScreen() {
		minecraft.displayGuiScreen(screenBefore);
	}
	
}
