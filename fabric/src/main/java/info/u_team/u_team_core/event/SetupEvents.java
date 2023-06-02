package info.u_team.u_team_core.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class SetupEvents {
	
	/**
	 * Will be fired after builtIn registries are frozen
	 */
	public static final Event<CommonSetupEvent> COMMON_SETUP = EventFactory.createArrayBacked(CommonSetupEvent.class, callbacks -> () -> {
		for (CommonSetupEvent callback : callbacks) {
			callback.onSetup();
		}
	});
	
	@FunctionalInterface
	public interface CommonSetupEvent {
		
		void onSetup();
	}
}
