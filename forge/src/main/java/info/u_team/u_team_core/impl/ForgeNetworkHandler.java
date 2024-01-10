package info.u_team.u_team_core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.EventNetworkChannel;
import net.minecraftforge.network.PacketDistributor;

public class ForgeNetworkHandler extends CommonNetworkHandler {
	
	private final Map<ResourceLocation, Pair<NetworkPayload<?>, Supplier<EventNetworkChannel>>> messages;
	
	ForgeNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
		messages = new HashMap<>();
	}
	
	@Override
	public <M> NetworkMessage<M> register(int index, NetworkPayload<M> payload) {
		final ResourceLocation messageId = channel.withSuffix("/" + index);
		
		validateNetworkPayload(messageId, payload);
		
		if (messages.containsKey(messageId)) {
			throw new IllegalArgumentException("Duplicate message id " + messageId);
		}
		
		final Supplier<EventNetworkChannel> network = Suppliers.memoize(() -> ChannelBuilder.named(messageId) //
				.networkProtocolVersion(protocolVersion) //
				.optionalServer() //
				.eventNetworkChannel());
		
		messages.put(messageId, Pair.of(payload, network));
		
		return new ForgeNetworkMessage<>(network, payload);
	}
	
	@Override
	public void register() {
		for (final Entry<ResourceLocation, Pair<NetworkPayload<?>, Supplier<EventNetworkChannel>>> entry : messages.entrySet()) {
			final ResourceLocation id = entry.getKey();
			final NetworkPayload<?> payload = entry.getValue().getFirst();
			final EventNetworkChannel network = entry.getValue().getSecond().get();
			
			network.addListener(event -> {
				if (!event.getChannel().equals(id)) {
					return;
				}
				final Object message = payload.read(event.getPayload());
				
				payload.handle(CastUtil.uncheckedCast(message), new ForgeNetworkContext(event.getSource()));
				event.getSource().setPacketHandled(true);
			});
		}
	}
	
	public static class ForgeNetworkMessage<M> implements NetworkMessage<M> {
		
		private final Supplier<EventNetworkChannel> network;
		private final NetworkPayload<M> payload;
		
		ForgeNetworkMessage(Supplier<EventNetworkChannel> network, NetworkPayload<M> payload) {
			this.network = network;
			this.payload = payload;
		}
		
		@Override
		public void sendToPlayer(ServerPlayer player, M message) {
			network.get().send(writeMessage(message), PacketDistributor.PLAYER.with(player));
		}
		
		@Override
		public void sendToConnection(Connection connection, M message) {
			network.get().send(writeMessage(message), connection);
		}
		
		@Override
		public void sendToServer(M message) {
			network.get().send(writeMessage(message), PacketDistributor.SERVER.noArg());
		}
		
		private FriendlyByteBuf writeMessage(M message) {
			final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
			payload.write(message, buffer);
			return buffer;
		}
		
	}
	
	public static class ForgeNetworkContext implements NetworkContext {
		
		private final CustomPayloadEvent.Context context;
		
		ForgeNetworkContext(CustomPayloadEvent.Context context) {
			this.context = context;
		}
		
		@Override
		public NetworkEnvironment getEnvironment() {
			return switch (context.getDirection().getReceptionSide()) {
			case CLIENT -> NetworkEnvironment.CLIENT;
			case SERVER -> NetworkEnvironment.SERVER;
			};
		}
		
		@Override
		public Player getPlayer() {
			if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
				return DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> Client.getClientPlayer());
			}
			return context.getSender();
		}
		
		@Override
		public CompletableFuture<Void> executeOnMainThread(Runnable runnable) {
			return context.enqueueWork(runnable);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	private class Client {
		
		@OnlyIn(Dist.CLIENT)
		private static Player getClientPlayer() {
			return Minecraft.getInstance().player;
		}
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation location, int protocolVersion) {
			return new ForgeNetworkHandler(location, protocolVersion);
		}
	}
	
}
