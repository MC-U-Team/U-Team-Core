package info.u_team.u_team_core.impl;

import java.util.concurrent.CompletableFuture;

import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler.MessagePacketPayload.CustomPacketPayloadImpl;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NeoForgeNetworkHandler extends CommonNetworkHandler {
	
	NeoForgeNetworkHandler(ResourceLocation networkId, int protocolVersion) {
		super(networkId, protocolVersion);
	}
	
	@Override
	protected <M> NetworkMessage<M> createMessage(MessagePacketPayload<M> messagePayload) {
		return new NeoForgeNetworkMessage<>(messagePayload);
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerPayloadHandlers));
	}
	
	private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(Integer.toString(protocolVersion)).executesOn(HandlerThread.NETWORK);
		
		for (final MessagePacketPayload<?> messagePayload : messages.values()) {
			final MessagePacketPayload.CustomPacketPayloadImpl pl = null;
			
			registrar.playBidirectional(pl.type(), pl.streamCodec(), (payload, context) -> {
				payload.ha
				
			});
		}
	}
	
	public static class NeoForgeNetworkMessage<M> extends CommonNetworkMessage<M> {
		
		NeoForgeNetworkMessage(MessagePacketPayload<M> messagePayload) {
			super(messagePayload);
		}
		
		@Override
		public void sendPacketToPlayer(ServerPlayer player, M message) {
			PacketDistributor.sendToPlayer(player, messagePayload);
		}
		
		@Override
		public void sendPacketToConnection(Connection connection, M message) {
			connection.send(null); // TODO
			// CONNECTION.with(connection).send(new PacketPayload<>(messagePayload, message));
		}
		
		@Override
		public void sendPacketToServer(M message) {
			PacketDistributor.sendToServer(messagePayload);
		}
	}
	
	public static class NeoForgeNetworkContext<M> extends CommonNetworkContext<M> {
		
		private final IPayloadContext context;
		
		NeoForgeNetworkContext(MessagePacketPayload<M> messagePayload, IPayloadContext context) {
			super(messagePayload);
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
			return context.player();
		}
		
		@Override
		protected CompletableFuture<Void> execute(Runnable runnable) {
			return context.enqueueWork(runnable);
		}
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation channel, int protocolVersion) {
			return new NeoForgeNetworkHandler(channel, protocolVersion);
		}
	}
}
