package info.u_team.u_team_core.screen;

import info.u_team.u_team_core.api.gui.IRenderTickable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class UScreen extends Screen {
	
	public UScreen(ITextComponent title) {
		super(title);
	}
	
	@Override
	public void tick() {
		children.forEach(listener -> {
			if (listener instanceof IRenderTickable) {
				((IRenderTickable) listener).renderTick();
			}
		});
	}
}
