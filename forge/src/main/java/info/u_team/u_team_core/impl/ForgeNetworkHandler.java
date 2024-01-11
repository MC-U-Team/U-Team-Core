package info.u_team.u_team_core.impl;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.EnvironmentUtil;
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
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.EventNetworkChannel;
import net.minecraftforge.network.PacketDistributor;

public class ForgeNetworkHandler extends CommonNetworkHandler {
	
	ForgeNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
	}
	
	@Override
	protected <M> NetworkMessage<M> createMessage(MessageNetworkPayload<M> messagePayload) {
		return new ForgeNetworkMessage<>(CastUtil.uncheckedCast(messagePayload));
	}
	
	@Override
	protected <M> MessageNetworkPayload<M> createMessageNetworkPayload(ResourceLocation messageId, NetworkPayload<M> payload) {
		return new ForgeMessageNetworkPayload<>(messageId, payload);
	}
	
	@Override
	public void register() {
		for (final MessageNetworkPayload<?> messagePayload : messages) {
			final ForgeMessageNetworkPayload<?> forgeMessagePayload = (ForgeMessageNetworkPayload<?>) messagePayload;
			
			forgeMessagePayload.getNetwork().addListener(event -> {
				if (!event.getChannel().equals(forgeMessagePayload.getMessageId())) {
					return;
				}
				
				final Object message = forgeMessagePayload.read(event.getPayload());
				forgeMessagePayload.handle(CastUtil.uncheckedCast(message), new ForgeNetworkContext(forgeMessagePayload.getMessageId(), event.getSource()));
				
				event.getSource().setPacketHandled(true);
			});
		}
	}
	
	private class ForgeMessageNetworkPayload<M> extends MessageNetworkPayload<M> {
		
		private final Supplier<EventNetworkChannel> network;
		
		private ForgeMessageNetworkPayload(ResourceLocation messageId, NetworkPayload<M> payload) {
			super(messageId, payload);
			network = Suppliers.memoize(() -> ChannelBuilder.named(messageId) //
					.networkProtocolVersion(protocolVersion) //
					.optionalServer() //
					.eventNetworkChannel());
		}
		
		public EventNetworkChannel getNetwork() {
			return network.get();
		}
		
	}
	
	public static class ForgeNetworkMessage<M> extends CommonNetworkMessage<M> {
		
		private final ForgeMessageNetworkPayload<M> forgeMessagePayload;
		
		ForgeNetworkMessage(ForgeMessageNetworkPayload<M> messagePayload) {
			super(messagePayload);
			this.forgeMessagePayload = messagePayload;
		}
		
		@Override
		public void sendPacketToPlayer(ServerPlayer player, M message) {
			forgeMessagePayload.getNetwork().send(writeMessage(message), PacketDistributor.PLAYER.with(player));
		}
		
		@Override
		public void sendPacketToConnection(Connection connection, M message) {
			forgeMessagePayload.getNetwork().send(writeMessage(message), connection);
		}
		
		@Override
		public void sendPacketToServer(M message) {
			forgeMessagePayload.getNetwork().send(writeMessage(message), PacketDistributor.SERVER.noArg());
		}
		
		private FriendlyByteBuf writeMessage(M message) {
			final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
			forgeMessagePayload.write(message, buffer);
			return buffer;
		}
		
	}
	
	public static class ForgeNetworkContext extends CommonNetworkContext {
		
		private final CustomPayloadEvent.Context context;
		
		ForgeNetworkContext(ResourceLocation messageId, CustomPayloadEvent.Context context) {
			super(messageId);
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
				return EnvironmentUtil.callWhen(Environment.CLIENT, () -> () -> Client.getClientPlayer());
			}
			return context.getSender();
		}
		
		@Override
		public CompletableFuture<Void> execute(Runnable runnable) {
			return context.enqueueWork(runnable);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	private class Client {
		
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
