package info.u_team.u_team_core.api.network;

import java.util.Set;

import net.minecraft.network.FriendlyByteBuf;

public interface NetworkPayload<M> {
	
	Set<NetworkEnvironment> getHandlerEnvironment();
	
	void write(M message, FriendlyByteBuf buffer);
	
	M read(FriendlyByteBuf buffer);
	
	void handle(M message, NetworkContext context);
	
}
