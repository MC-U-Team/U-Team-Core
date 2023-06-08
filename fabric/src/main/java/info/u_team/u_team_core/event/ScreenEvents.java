package info.u_team.u_team_core.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.Screen;

public class ScreenEvents {
	
	/**
	 * Will be fired for key presses in screens after the gui key binds are handled
	 */
	public static final Event<ScreenAfterKeyPressed> AFTER_KEY_PRESSED = EventFactory.createArrayBacked(ScreenAfterKeyPressed.class, callbacks -> (screen, keyCode, scanCode, modifiers) -> {
		for (final ScreenAfterKeyPressed callback : callbacks) {
			if (callback.onKeyPressed(screen, keyCode, scanCode, modifiers)) {
				return true;
			}
		}
		return false;
	});
	
	@FunctionalInterface
	public interface ScreenAfterKeyPressed {
		
		boolean onKeyPressed(Screen screen, int keyCode, int scanCode, int modifiers);
	}
	
}
