package info.u_team.u_team_core.screen;

import info.u_team.u_team_core.api.gui.IRenderTickable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class UScreen extends Screen {

	public UScreen(Component title) {
		super(title);
	}

	@Override
	public void tick() {
		children().forEach(listener -> { // TODO change to direct variable?
			if (listener instanceof IRenderTickable) {
				((IRenderTickable) listener).renderTick();
			}
		});
	}
}
