package info.u_team.u_team_core.intern.discord;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class DiscordConfigScreen extends Screen {
	
	private final Screen screenBefore;
	
	public DiscordConfigScreen(Screen screenBefore) {
		super(ITextComponent.getTextComponentOrEmpty("discord"));
		this.screenBefore = screenBefore;
	}
	
	@Override
	public void closeScreen() {
		minecraft.displayGuiScreen(screenBefore);
	}
	
}
