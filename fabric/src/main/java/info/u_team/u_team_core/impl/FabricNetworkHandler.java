package info.u_team.u_team_core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.EnvironmentUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;

public class FabricNetworkHandler extends CommonNetworkHandler {
	
	private final Map<ResourceLocation, NetworkPayload<?>> messages;
	
	FabricNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
		messages = new HashMap<>();
	}
	
	@Override
	public <M> NetworkMessage<M> register(int index, NetworkPayload<M> payload) {
		final ResourceLocation messageId = channel.withSuffix("/" + index);
		
		validateNetworkPayload(messageId, payload);
		
		if (messages.putIfAbsent(messageId, payload) != null) {
			throw new IllegalArgumentException("Duplicate message id " + messageId);
		}
		
		return new FabricNetworkMessage<>(messageId, payload);
	}
	
	@Override
	public void register() {
		for (final Entry<ResourceLocation, NetworkPayload<?>> entry : messages.entrySet()) {
			final ResourceLocation id = entry.getKey();
			final NetworkPayload<?> payload = entry.getValue();
			final Set<NetworkEnvironment> list = payload.getHandlerEnvironment();
			
			if (list.contains(NetworkEnvironment.CLIENT)) {
				EnvironmentUtil.runWhen(Environment.CLIENT, () -> () -> Client.registerReceiver(id, payload));
			}
			if (list.contains(NetworkEnvironment.SERVER)) {
				ServerPlayNetworking.registerGlobalReceiver(id, (server, player, handler, buffer, responseSender) -> {
					final Object message = payload.read(buffer);
					
					payload.handle(CastUtil.uncheckedCast(message), new FabricNetworkContext(NetworkEnvironment.SERVER, player, server));
				});
			}
		}
	}
	
	private static class Client {
		
		public static void send(ResourceLocation messageId, FriendlyByteBuf buffer) {
			ClientPlayNetworking.send(messageId, buffer);
		}
		
		public static void registerReceiver(ResourceLocation id, NetworkPayload<?> payload) {
			ClientPlayNetworking.registerGlobalReceiver(id, (client, packetListener, buffer, responseSender) -> {
				final Object message = payload.read(buffer);
				
				payload.handle(CastUtil.uncheckedCast(message), new FabricNetworkContext(NetworkEnvironment.CLIENT, client.player, client));
			});
		}
	}
	
	public static class FabricNetworkMessage<M> implements NetworkMessage<M> {
		
		private final ResourceLocation messageId;
		private final NetworkPayload<M> payload;
		
		FabricNetworkMessage(ResourceLocation messageId, NetworkPayload<M> payload) {
			this.messageId = messageId;
			this.payload = payload;
		}
		
		@Override
		public void sendToPlayer(ServerPlayer player, M message) {
			ServerPlayNetworking.send(player, messageId, writeMessage(message));
		}
		
		@Override
		public void sendToConnection(Connection connection, M message) {
			connection.send(ServerPlayNetworking.createS2CPacket(messageId, writeMessage(message)));
		}
		
		@Override
		public void sendToServer(M message) {
			EnvironmentUtil.runWhen(Environment.CLIENT, () -> () -> Client.send(messageId, writeMessage(message)));
		}
		
		private FriendlyByteBuf writeMessage(M message) {
			final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
			payload.write(message, buffer);
			return buffer;
		}
		
	}
	
	public static class FabricNetworkContext implements NetworkContext {
		
		private final NetworkEnvironment environment;
		private final Player player;
		private final BlockableEventLoop<?> executor;
		
		FabricNetworkContext(NetworkEnvironment environment, Player player, BlockableEventLoop<?> executor) {
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
		public void executeOnMainThread(Runnable runnable) {
			if (!executor.isSameThread()) {
				executor.submitAsync(runnable);
			} else {
				runnable.run();
			}
		}
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation location, int protocolVersion) {
			return new FabricNetworkHandler(location, protocolVersion);
		}
	}
}
