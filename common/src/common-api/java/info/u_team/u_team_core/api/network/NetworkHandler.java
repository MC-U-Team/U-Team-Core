package info.u_team.u_team_core.api.network;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public interface NetworkHandler {
	
	static NetworkHandler create(int protocolVersion, ResourceLocation location) {
		return Factory.INSTANCE.create(protocolVersion, location);
	}
	
	default <M> void registerMessage(int index, Class<M> clazz, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, NetworkContext> messageConsumer) {
		registerMessage(index, clazz, encoder, decoder, messageConsumer, Optional.empty());
	}
	
	<M> void registerMessage(int index, Class<M> clazz, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, NetworkContext> messageConsumer, Optional<NetworkEnvironment> handlerEnvironment);
	
	<M> void sendToPlayer(ServerPlayer player, M message);
	
	<M> void sendToServer(M message);
	
	int getProtocolVersion();
	
	void setProtocolAcceptor(Predicate<Integer> clientAcceptedVersions, Predicate<Integer> serverAcceptedVersions);
	
	interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		NetworkHandler create(int protocolVersion, ResourceLocation location);
	}
}
