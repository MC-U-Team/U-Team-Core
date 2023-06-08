package info.u_team.u_team_core.api.network;

import net.minecraft.world.entity.player.Player;

public interface NetworkContext {
	
	NetworkEnvironment getEnvironment();
	
	Player getPlayer();
	
	void executeOnMainThread(Runnable runnable);
}
