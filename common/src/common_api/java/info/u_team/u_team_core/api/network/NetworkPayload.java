package info.u_team.u_team_core.api.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface NetworkPayload<M> {
	
	StreamCodec<? super ByteBuf, M> streamCodec();
	
	NetworkHandlerEnvironment handlerEnvironment();
	
	void handle(M message, NetworkContext context);
	
}
