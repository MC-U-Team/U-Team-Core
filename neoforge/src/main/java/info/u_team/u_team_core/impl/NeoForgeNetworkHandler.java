package info.u_team.u_team_core.impl;

import java.util.concurrent.CompletableFuture;

import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkHandlerEnvironment;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NeoForgeNetworkHandler extends CommonNetworkHandler {
	
	NeoForgeNetworkHandler(ResourceLocation networkId, int protocolVersion) {
		super(networkId, protocolVersion);
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerPayloadHandlers));
	}
	
	private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(Integer.toString(protocolVersion)).executesOn(HandlerThread.NETWORK);
		
		for (final MessagePacketPayload<?> messagePayload : messages.values()) {
			final NetworkHandlerEnvironment environment = messagePayload.payload().handlerEnvironment();
			final IPayloadHandler<CustomPacketPayload> handler = (payload, context) -> {
				messagePayload.handle(payload, new NeoForgeNetworkContext<>(messagePayload, context));
			};
			
			if (environment == NetworkHandlerEnvironment.CLIENT) {
				registrar.playToClient(messagePayload.type(), messagePayload.streamCodec(), handler);
			} else if (environment == NetworkHandlerEnvironment.SERVER) {
				registrar.playToServer(messagePayload.type(), messagePayload.streamCodec(), handler);
			} else if (environment == NetworkHandlerEnvironment.BOTH) {
				registrar.playBidirectional(messagePayload.type(), messagePayload.streamCodec(), handler);
			}
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
