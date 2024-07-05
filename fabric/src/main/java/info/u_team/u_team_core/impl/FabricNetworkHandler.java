package info.u_team.u_team_core.impl;

import java.util.concurrent.CompletableFuture;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkHandlerEnvironment;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.EnvironmentUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;

public class FabricNetworkHandler extends CommonNetworkHandler {
	
	FabricNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
	}
	
	@Override
	public void register() {
		for (final MessagePacketPayload<?> messagePayload : messages.values()) {
			final NetworkHandlerEnvironment environment = messagePayload.payload().handlerEnvironment();
			
			if (environment.isValid(NetworkEnvironment.CLIENT)) {
				PayloadTypeRegistry.playS2C().register(messagePayload.type(), messagePayload.streamCodec());
				EnvironmentUtil.runWhen(Environment.CLIENT, () -> () -> Client.registerReceiver(messagePayload));
			}
			if (environment.isValid(NetworkEnvironment.SERVER)) {
				PayloadTypeRegistry.playC2S().register(messagePayload.type(), messagePayload.streamCodec());
				ServerPlayNetworking.registerGlobalReceiver(messagePayload.type(), (payload, context) -> {
					messagePayload.handle(payload, new FabricNetworkContext<>(messagePayload, NetworkEnvironment.SERVER, context.player(), context.player().server));
					context.player().getServer();
				});
			}
		}
	}
	
	private static class FabricNetworkContext<M> extends CommonNetworkContext<M> {
		
		private final NetworkEnvironment environment;
		private final Player player;
		private final BlockableEventLoop<?> executor;
		
		private FabricNetworkContext(MessagePacketPayload<M> messagePayload, NetworkEnvironment environment, Player player, BlockableEventLoop<?> executor) {
			super(messagePayload);
			this.environment = environment;
			this.player = player;
			this.executor = executor;
		}
		
		@Override
		public NetworkEnvironment getEnvironment() {
			return environment;
		}
		
		@Override
		public Player getPlayer() {
			return player;
		}
		
		@Override
		public CompletableFuture<Void> execute(Runnable runnable) {
			if (!executor.isSameThread()) {
				return executor.submitAsync(runnable);
			} else {
				runnable.run();
				return CompletableFuture.completedFuture(null);
			}
		}
	}
	
	private static class Client {
		
		private static void registerReceiver(MessagePacketPayload<?> messagePayload) {
			ClientPlayNetworking.registerGlobalReceiver(messagePayload.type(), (payload, context) -> {
				messagePayload.handle(payload, new FabricNetworkContext<>(messagePayload, NetworkEnvironment.CLIENT, context.player(), context.client()));
				context.player().getServer();
			});
		}
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation location, int protocolVersion) {
			return new FabricNetworkHandler(location, protocolVersion);
		}
	}
}
