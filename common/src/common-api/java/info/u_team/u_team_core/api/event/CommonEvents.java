package info.u_team.u_team_core.api.event;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface CommonEvents {
	
	static void registerSetup(SetupEvent event) {
		Handler.INSTANCE.registerSetup(event);
	}
	
	static void registerRegister(RegisterEvent event) {
		Handler.INSTANCE.registerRegister(event);
	}
	
	interface Handler {
		
		Handler INSTANCE = ServiceUtil.loadOne(Handler.class);
		
		void registerSetup(SetupEvent event);
		
		void registerRegister(RegisterEvent event);
	}
	
	@FunctionalInterface
	interface SetupEvent {
		
		void onSetup();
	}
	
	@FunctionalInterface
	public interface RegisterEvent {
		
		void onRegister(ResourceKey<? extends Registry<?>> key);
	}
	
}
