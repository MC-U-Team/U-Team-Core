package info.u_team.u_team_core.api.network;

import java.util.Set;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface NetworkPayload<M> {
	
	StreamCodec<? extends ByteBuf, M> streamCodec();
	
	Set<NetworkEnvironment> handlerEnvironment();
	
	void handle(M message, NetworkContext context);
	
}
