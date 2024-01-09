package info.u_team.u_team_core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class NeoForgeNetworkHandler implements NetworkHandler {
	
	private final ResourceLocation channel;
	private final int protocolVersion;
	
	private final Map<ResourceLocation, NetworkPayload<?>> messages;
	
	NeoForgeNetworkHandler(ResourceLocation channel, int protocolVersion) {
		this.channel = channel;
		this.protocolVersion = protocolVersion;
		messages = new HashMap<>();
	}
	
	@Override
	public <M> NeoForgeNetworkMessage<M> register(int index, NetworkPayload<M> payload) {
		final ResourceLocation messageId = channel.withSuffix("/" + index);
		
		if (payload.getHandlerEnvironment().isEmpty()) {
			throw new IllegalArgumentException("Handler environment cannot be empty for message id " + messageId);
		}
		
		if (messages.putIfAbsent(messageId, payload) != null) {
			throw new IllegalArgumentException("Duplicate message id " + messageId);
		}
		
		return new NeoForgeNetworkMessage<>(messageId, payload);
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerPayloadHandler));
	}
	
	private void registerPayloadHandler(RegisterPayloadHandlerEvent event) {
		final IPayloadRegistrar registrar = event.registrar(channel.getNamespace()).versioned(Integer.toString(protocolVersion));
		
		for (final Entry<ResourceLocation, NetworkPayload<?>> entry : messages.entrySet()) {
			final ResourceLocation id = entry.getKey();
			final NetworkPayload<?> payload = entry.getValue();
			
			registrar.play(entry.getKey(), buffer -> new PacketPayload<>(id, payload, buffer), handlers -> {
				final Set<NetworkEnvironment> list = payload.getHandlerEnvironment();
				
				if (list.contains(NetworkEnvironment.CLIENT)) {
					handlers.client(PacketPayload::handle);
				}
				if (list.contains(NetworkEnvironment.CLIENT)) {
					handlers.server(PacketPayload::handle);
				}
			});
		}
	}
	
	@Override
	public ResourceLocation getChannel() {
		return channel;
	}
	
	@Override
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	private static class PacketPayload<M> implements CustomPacketPayload {
		
		private final ResourceLocation id;
		private final NetworkPayload<M> payload;
		private final M message;
		
		private PacketPayload(ResourceLocation id, NetworkPayload<M> payload, FriendlyByteBuf buffer) {
			this(id, payload, payload.read(buffer));
		}
		
		private PacketPayload(ResourceLocation id, NetworkPayload<M> payload, M message) {
			this.id = id;
			this.payload = payload;
			this.message = message;
		}
		
		@Override
		public void write(FriendlyByteBuf buffer) {
			payload.write(message, buffer);
		}
		
		@Override
		public ResourceLocation id() {
			return id;
		}
		
		private void handle(IPayloadContext context) {
			payload.handle(message, new NeoForgeNetworkContext(context));
		}
		
	}
	
	public static class NeoForgeNetworkMessage<M> implements NetworkMessage<M> {
		
		public static final PacketDistributor<Connection> CONNECTION = new PacketDistributor<>((distributor, connection) -> packet -> connection.send(packet), PacketFlow.CLIENTBOUND);
		
		private final ResourceLocation messageId;
		private final NetworkPayload<M> payload;
		
		NeoForgeNetworkMessage(ResourceLocation messageId, NetworkPayload<M> payload) {
			this.messageId = messageId;
			this.payload = payload;
		}
		
		@Override
		public void sendToPlayer(ServerPlayer player, M message) {
			PacketDistributor.PLAYER.with(player).send(new PacketPayload<>(messageId, payload, message));
		}
		
		@Override
		public void sendToConnection(Connection connection, M message) {
			CONNECTION.with(connection).send(new PacketPayload<>(messageId, payload, message));
		}
		
		@Override
		public void sendToServer(M message) {
			PacketDistributor.SERVER.noArg().send(new PacketPayload<>(messageId, payload, message));
		}
		
	}
	
	public static class NeoForgeNetworkContext implements NetworkContext {
		
		private final IPayloadContext context;
		
		NeoForgeNetworkContext(IPayloadContext context) {
			this.context = context;
		}
		
		@Override
		public NetworkEnvironment getEnvironment() {
			return switch (context.flow().getReceptionSide()) {
			case CLIENT -> NetworkEnvironment.CLIENT;
			case SERVER -> NetworkEnvironment.SERVER;
			};
		}
		
		@Override
		public Player getPlayer() {
			return context.player().orElse(null);
		}
		
		@Override
		public void executeOnMainThread(Runnable runnable) {
			context.workHandler().execute(runnable);
		}
		
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation channel, int protocolVersion) {
			return new NeoForgeNetworkHandler(channel, protocolVersion);
		}
	}
}
