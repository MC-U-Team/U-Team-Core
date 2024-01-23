package info.u_team.u_team_core.api.network;

import java.util.concurrent.CompletableFuture;

import net.minecraft.world.entity.player.Player;

public interface NetworkContext {
	
	NetworkEnvironment getEnvironment();
	
	Player getPlayer();
	
	CompletableFuture<Void> executeOnMainThread(Runnable runnable);
}
