package info.u_team.u_team_core.impl;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
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

public class NeoForgeNetworkHandler extends CommonNetworkHandler {
	
	NeoForgeNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
	}
	
	@Override
	protected <M> NetworkMessage<M> createMessage(MessageNetworkPayload<M> messagePayload) {
		return new NeoForgeNetworkMessage<>(messagePayload);
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerPayloadHandler));
	}
	
	private void registerPayloadHandler(RegisterPayloadHandlerEvent event) {
		final IPayloadRegistrar registrar = event.registrar(channel.getNamespace()).versioned(Integer.toString(protocolVersion));
		
		for (final MessageNetworkPayload<?> messagePayload : messages) {
			registrar.play(messagePayload.getMessageId(), buffer -> new PacketPayload<>(messagePayload, buffer), handlers -> {
				final Set<NetworkEnvironment> list = messagePayload.getPayload().getHandlerEnvironment();
				
				if (list.contains(NetworkEnvironment.CLIENT)) {
					handlers.client(PacketPayload::handle);
				}
				if (list.contains(NetworkEnvironment.CLIENT)) {
					handlers.server(PacketPayload::handle);
				}
			});
		}
	}
	
	private static class PacketPayload<M> implements CustomPacketPayload {
		
		private final MessageNetworkPayload<M> messagePayload;
		private final M message;
		
		private PacketPayload(MessageNetworkPayload<M> messagePayload, FriendlyByteBuf buffer) {
			this(messagePayload, messagePayload.read(buffer));
		}
		
		private PacketPayload(MessageNetworkPayload<M> messagePayload, M message) {
			this.messagePayload = messagePayload;
			this.message = message;
		}
		
		@Override
		public void write(FriendlyByteBuf buffer) {
			messagePayload.write(message, buffer);
		}
		
		@Override
		public ResourceLocation id() {
			return messagePayload.getMessageId();
		}
		
		private void handle(IPayloadContext context) {
			messagePayload.handle(message, new NeoForgeNetworkContext(messagePayload.getMessageId(), context));
		}
	}
	
	public static class NeoForgeNetworkMessage<M> extends CommonNetworkMessage<M> {
		
		public static final PacketDistributor<Connection> CONNECTION = new PacketDistributor<>((distributor, connection) -> packet -> connection.send(packet), PacketFlow.CLIENTBOUND);
		
		NeoForgeNetworkMessage(MessageNetworkPayload<M> messagePayload) {
			super(messagePayload);
		}
		
		@Override
		public void sendPacketToPlayer(ServerPlayer player, M message) {
			PacketDistributor.PLAYER.with(player).send(new PacketPayload<>(messagePayload, message));
		}
		
		@Override
		public void sendPacketToConnection(Connection connection, M message) {
			CONNECTION.with(connection).send(new PacketPayload<>(messagePayload, message));
		}
		
		@Override
		public void sendPacketToServer(M message) {
			PacketDistributor.SERVER.noArg().send(new PacketPayload<>(messagePayload, message));
		}
	}
	
	public static class NeoForgeNetworkContext extends CommonNetworkContext {
		
		private final IPayloadContext context;
		
		NeoForgeNetworkContext(ResourceLocation messageId, IPayloadContext context) {
			super(messageId);
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
		protected CompletableFuture<Void> execute(Runnable runnable) {
			return context.workHandler().submitAsync(runnable);
		}
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation channel, int protocolVersion) {
			return new NeoForgeNetworkHandler(channel, protocolVersion);
		}
	}
}
