package info.u_team.u_team_core.api.network;

import java.util.function.BiConsumer;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface NetworkPayload<M> {
	
	static <M> NetworkPayload<M> of(NetworkHandlerEnvironment handlerEnvironment, StreamCodec<? super RegistryFriendlyByteBuf, M> streamCodec, BiConsumer<M, NetworkContext> handler) {
		return new NetworkPayload<>() {
			
			@Override
			public StreamCodec<? super RegistryFriendlyByteBuf, M> streamCodec() {
				return streamCodec;
			}
			
			@Override
			public NetworkHandlerEnvironment handlerEnvironment() {
				return handlerEnvironment;
			}
			
			@Override
			public void handle(M message, NetworkContext context) {
				handler.accept(message, context);
			}
		};
	}
	
	StreamCodec<? super RegistryFriendlyByteBuf, M> streamCodec();
	
	NetworkHandlerEnvironment handlerEnvironment();
	
	void handle(M message, NetworkContext context);
	
}
