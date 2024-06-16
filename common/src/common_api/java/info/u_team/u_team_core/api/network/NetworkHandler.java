package info.u_team.u_team_core.api.network;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public interface NetworkHandler {
	
	static NetworkHandler create(ResourceLocation channel, int protocolVersion) {
		return Factory.INSTANCE.create(channel, protocolVersion);
	}
	
	default <M> NetworkMessage<M> register(int id, NetworkHandlerEnvironment handlerEnvironment, StreamCodec<? super RegistryFriendlyByteBuf, M> streamCodec, BiConsumer<M, NetworkContext> handler) {
		return register(id, NetworkPayload.of(handlerEnvironment, streamCodec, handler));
	}
	
	default <M> NetworkMessage<M> register(int index, NetworkPayload<M> payload) {
		return register(Integer.toString(index), payload);
	}
	
	default <M> NetworkMessage<M> register(String id, NetworkHandlerEnvironment handlerEnvironment, StreamCodec<? super RegistryFriendlyByteBuf, M> streamCodec, BiConsumer<M, NetworkContext> handler) {
		return register(id, NetworkPayload.of(handlerEnvironment, streamCodec, handler));
	}
	
	<M> NetworkMessage<M> register(String id, NetworkPayload<M> payload);
	
	void register();
	
	ResourceLocation getNetworkId();
	
	int getProtocolVersion();
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		NetworkHandler create(ResourceLocation channel, int protocolVersion);
	}
}
