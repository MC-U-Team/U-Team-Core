package info.u_team.u_team_core.api.event;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.client.Minecraft;

public interface ClientEvents {
	
	static void registerStartClientTick(StartClientTick event) {
		Handler.INSTANCE.registerStartClientTick(event);
	}
	
	static void registerEndClientTick(EndClientTick event) {
		Handler.INSTANCE.registerEndClientTick(event);
	}
	
	interface Handler {
		
		Handler INSTANCE = ServiceUtil.loadOne(Handler.class);
		
		void registerStartClientTick(StartClientTick event);
		
		void registerEndClientTick(EndClientTick event);
	}
	
	@FunctionalInterface
	interface StartClientTick {
		
		void onStartTick(Minecraft minecraft);
	}
	
	@FunctionalInterface
	interface EndClientTick {
		
		void onEndTick(Minecraft minecraft);
	}
}
