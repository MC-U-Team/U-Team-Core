package info.u_team.u_team_core.impl;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.EnvironmentUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;

public class FabricNetworkHandler extends CommonNetworkHandler {
	
	FabricNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
	}
	
	@Override
	protected <M> NetworkMessage<M> createMessage(MessageNetworkPayload<M> messagePayload) {
		return new FabricNetworkMessage<>(messagePayload);
	}
	
	@Override
	public void register() {
		for (final MessageNetworkPayload<?> messagePayload : messages) {
			final Set<NetworkEnvironment> list = messagePayload.getPayload().getHandlerEnvironment();
			
			if (list.contains(NetworkEnvironment.CLIENT)) {
				EnvironmentUtil.runWhen(Environment.CLIENT, () -> () -> Client.registerReceiver(messagePayload));
			}
			if (list.contains(NetworkEnvironment.SERVER)) {
				ServerPlayNetworking.registerGlobalReceiver(messagePayload.getMessageId(), (server, player, handler, buffer, responseSender) -> {
					final Object message = messagePayload.read(buffer);
					messagePayload.handle(CastUtil.uncheckedCast(message), new FabricNetworkContext(messagePayload.getMessageId(), NetworkEnvironment.SERVER, player, server));
				});
			}
		}
	}
	
	public static class FabricNetworkMessage<M> extends CommonNetworkMessage<M> {
		
		FabricNetworkMessage(MessageNetworkPayload<M> messagePayload) {
			super(messagePayload);
		}
		
		@Override
		public void sendPacketToPlayer(ServerPlayer player, M message) {
			ServerPlayNetworking.send(player, messagePayload.getMessageId(), writeMessage(message));
		}
		
		@Override
		public void sendPacketToConnection(Connection connection, M message) {
			connection.send(ServerPlayNetworking.createS2CPacket(messagePayload.getMessageId(), writeMessage(message)));
		}
		
		@Override
		public void sendPacketToServer(M message) {
			EnvironmentUtil.runWhen(Environment.CLIENT, () -> () -> Client.send(messagePayload.getMessageId(), writeMessage(message)));
		}
		
		private FriendlyByteBuf writeMessage(M message) {
			final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
			messagePayload.write(message, buffer);
			return buffer;
		}
		
	}
	
	public static class FabricNetworkContext extends CommonNetworkContext {
		
		private final NetworkEnvironment environment;
		private final Player player;
		private final BlockableEventLoop<?> executor;
		
		FabricNetworkContext(ResourceLocation messageId, NetworkEnvironment environment, Player player, BlockableEventLoop<?> executor) {
			super(messageId);
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
	
	@net.fabricmc.api.Environment(EnvType.CLIENT)
	private class Client {
		
		private static void send(ResourceLocation messageId, FriendlyByteBuf buffer) {
			ClientPlayNetworking.send(messageId, buffer);
		}
		
		private static void registerReceiver(MessageNetworkPayload<?> messagePayload) {
			ClientPlayNetworking.registerGlobalReceiver(messagePayload.getMessageId(), (client, packetListener, buffer, responseSender) -> {
				final Object message = messagePayload.read(buffer);
				
				messagePayload.handle(CastUtil.uncheckedCast(message), new FabricNetworkContext(messagePayload.getMessageId(), NetworkEnvironment.CLIENT, client.player, client));
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
