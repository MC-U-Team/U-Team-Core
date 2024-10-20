package info.u_team.u_team_core.impl;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkHandlerEnvironment;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.EnvironmentUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.payload.PayloadConnection;
import net.minecraftforge.network.payload.PayloadFlow;
import net.minecraftforge.network.payload.PayloadProtocol;

public class ForgeNetworkHandler extends CommonNetworkHandler {
	
	private Channel<CustomPacketPayload> forgeChannel;
	
	ForgeNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
	}
	
	@Override
	public <M> NetworkMessage<M> createNetworkMessage(MessagePacketPayload<M> messagePayload) {
		return new ForgeNetworkMessage<>(messagePayload, () -> forgeChannel);
	}
	
	@Override
	public void register() {
		final PayloadConnection<CustomPacketPayload> payloadChannel = ChannelBuilder.named(networkId).networkProtocolVersion(protocolVersion).optionalServer().payloadChannel();
		
		PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> lastMessage = null;
		for (final MessagePacketPayload<?> messagePayload : messages.values()) {
			final PayloadProtocol<RegistryFriendlyByteBuf, CustomPacketPayload> builder;
			if (lastMessage == null) {
				builder = payloadChannel.play();
			} else {
				builder = lastMessage;
			}
			
			final NetworkHandlerEnvironment environment = messagePayload.payload().handlerEnvironment();
			final BiConsumer<CustomPacketPayload, CustomPayloadEvent.Context> handler = (payload, context) -> {
				messagePayload.handle(payload, new ForgeNetworkContext<>(messagePayload, context));
				context.setPacketHandled(true);
			};
			
			if (environment == NetworkHandlerEnvironment.CLIENT) {
				lastMessage = builder.clientbound().add(messagePayload.type(), cast(messagePayload.streamCodec()), handler);
			} else if (environment == NetworkHandlerEnvironment.SERVER) {
				lastMessage = builder.serverbound().add(messagePayload.type(), cast(messagePayload.streamCodec()), handler);
			} else if (environment == NetworkHandlerEnvironment.BOTH) {
				lastMessage = builder.bidirectional().add(messagePayload.type(), cast(messagePayload.streamCodec()), handler);
			}
		}
		forgeChannel = lastMessage.build();
	}
	
	public static class ForgeNetworkMessage<M> extends CommonNetworkMessage<M> {
		
		private final Supplier<Channel<CustomPacketPayload>> forgeChannelSupplier;
		
		private ForgeNetworkMessage(MessagePacketPayload<M> messagePayload, Supplier<Channel<CustomPacketPayload>> forgeChannelSupplier) {
			super(messagePayload);
			this.forgeChannelSupplier = Suppliers.memoize(forgeChannelSupplier::get);
		}
		
		public Channel<CustomPacketPayload> forgeChannel() {
			final Channel<CustomPacketPayload> forgeChannel = forgeChannelSupplier.get();
			if (forgeChannel == null) {
				throw new IllegalStateException("Forge channel has not been registered");
			}
			return forgeChannel;
		}
		
		@Override
		public Packet<?> packet(NetworkEnvironment toEnvironment, M message) {
			final Channel<CustomPacketPayload> forgeChannel = forgeChannel();
			final CustomPacketPayload payload = packet(message);
			
			return switch (toEnvironment) {
			case CLIENT -> NetworkDirection.PLAY_TO_CLIENT.buildPacket(forgeChannel, payload);
			case SERVER -> NetworkDirection.PLAY_TO_SERVER.buildPacket(forgeChannel, payload);
			};
		}
		
	}
	
	private static class ForgeNetworkContext<M> extends CommonNetworkContext<M> {
		
		private final CustomPayloadEvent.Context context;
		
		private ForgeNetworkContext(MessagePacketPayload<M> messagePayload, CustomPayloadEvent.Context context) {
			super(messagePayload);
			this.context = context;
		}
		
		@Override
		public NetworkEnvironment getEnvironment() {
			return context.isClientSide() ? NetworkEnvironment.CLIENT : NetworkEnvironment.SERVER;
		}
		
		@Override
		public Player getPlayer() {
			if (context.isClientSide()) {
				return EnvironmentUtil.callWhen(Environment.CLIENT, () -> () -> Client.getClientPlayer());
			}
			return context.getSender();
		}
		
		@Override
		public CompletableFuture<Void> execute(Runnable runnable) {
			return context.enqueueWork(runnable);
		}
	}
	
	private static class Client {
		
		private static Player getClientPlayer() {
			return Minecraft.getInstance().player;
		}
	}
	
	private StreamCodec<RegistryFriendlyByteBuf, CustomPacketPayload> cast(StreamCodec<? super RegistryFriendlyByteBuf, CustomPacketPayload> codec) {
		return CastUtil.uncheckedCast(codec);
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation location, int protocolVersion) {
			return new ForgeNetworkHandler(location, protocolVersion);
		}
	}
	
}
