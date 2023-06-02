package info.u_team.u_team_core.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class SetupEvents {
	
	/**
	 * Will be fired for each registry
	 */
	public static final Event<RegisterEvent> REGISTER = EventFactory.createArrayBacked(RegisterEvent.class, callbacks -> key -> {
		for (RegisterEvent callback : callbacks) {
			callback.onRegister(key);
		}
	});
	
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
	
	@FunctionalInterface
	public interface RegisterEvent {
		
		void onRegister(ResourceKey<? extends Registry<?>> key);
	}
}
