package info.u_team.u_team_core.api.network;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;

public interface NetworkMessage<M> {
	
	void sendToPlayer(ServerPlayer player, M message);
	
	void sendToConnection(Connection connection, M message);
	
	void sendToServer(M message);
	
}
