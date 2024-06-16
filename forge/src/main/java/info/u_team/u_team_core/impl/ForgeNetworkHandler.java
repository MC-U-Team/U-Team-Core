package info.u_team.u_team_core.impl;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkHandlerEnvironment;
import info.u_team.u_team_core.impl.common.CommonNetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.EnvironmentUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.payload.PayloadConnection;
import net.minecraftforge.network.payload.PayloadFlow;
import net.minecraftforge.network.payload.PayloadProtocol;

public class ForgeNetworkHandler extends CommonNetworkHandler {
	
	ForgeNetworkHandler(ResourceLocation channel, int protocolVersion) {
		super(channel, protocolVersion);
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
			};
			
			if (environment == NetworkHandlerEnvironment.CLIENT) {
				lastMessage = builder.clientbound().add(messagePayload.type(), cast(messagePayload.streamCodec()), handler);
			} else if (environment == NetworkHandlerEnvironment.SERVER) {
				lastMessage = builder.serverbound().add(messagePayload.type(), cast(messagePayload.streamCodec()), handler);
			} else if (environment == NetworkHandlerEnvironment.BOTH) {
				lastMessage = builder.bidirectional().add(messagePayload.type(), cast(messagePayload.streamCodec()), handler);
			}
		}
		lastMessage.build();
	}
	
	public static class ForgeNetworkContext<M> extends CommonNetworkContext<M> {
		
		private final CustomPayloadEvent.Context context;
		
		ForgeNetworkContext(MessagePacketPayload<M> messagePayload, CustomPayloadEvent.Context context) {
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
	
	private class Client {
		
		private static Player getClientPlayer() {
			return Minecraft.getInstance().player;
		}
	}
	
	private StreamCodec<RegistryFriendlyByteBuf, CustomPacketPayload> cast(StreamCodec<? super ByteBuf, CustomPacketPayload> codec) {
		return CastUtil.uncheckedCast(codec);
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(ResourceLocation location, int protocolVersion) {
			return new ForgeNetworkHandler(location, protocolVersion);
		}
	}
	
}
