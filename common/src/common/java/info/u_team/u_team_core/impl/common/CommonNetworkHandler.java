package info.u_team.u_team_core.impl.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.api.network.NetworkPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.Connection;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public abstract class CommonNetworkHandler implements NetworkHandler {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger("NetworkHandler");
	
	protected final ResourceLocation networkId;
	protected final int protocolVersion;
	
	protected final Map<CustomPacketPayload.Type<?>, MessagePacketPayload<?>> messages;
	
	protected CommonNetworkHandler(ResourceLocation networkId, int protocolVersion) {
		this.networkId = networkId;
		this.protocolVersion = protocolVersion;
		messages = new HashMap<>();
	}
	
	protected abstract <M> NetworkMessage<M> createMessage(MessagePacketPayload<M> messagePayload);
	
	@Override
	public <M> NetworkMessage<M> register(String id, NetworkPayload<M> payload) {
		final ResourceLocation messageId = networkId.withSuffix("/" + id);
		
		if (payload.handlerEnvironment().isEmpty()) {
			throw new IllegalArgumentException("Handler environment cannot be empty for message id " + messageId);
		}
		
		final MessagePacketPayload<M> messagePayload = new MessagePacketPayload<>(messageId, payload);
		
		if (messages.put(messagePayload.type, messagePayload) != null) {
			throw new IllegalArgumentException("Duplicate message id " + messageId);
		}
		
		return createMessage(messagePayload);
	}
	
	@Override
	public ResourceLocation getNetworkId() {
		return networkId;
	}
	
	@Override
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	protected static class MessagePacketPayload<M> {
		
		private final CustomPacketPayload.Type<CustomPacketPayloadImpl> type;
		private final NetworkPayload<M> payload;
		
		protected MessagePacketPayload(ResourceLocation messageId, NetworkPayload<M> payload) {
			this.type = new CustomPacketPayload.Type<>(messageId);
			this.payload = payload;
		}
		
		public CustomPacketPayload.Type<CustomPacketPayloadImpl> type() {
			return type;
		}
		
		public StreamCodec<? extends ByteBuf, CustomPacketPayloadImpl> streamCodec() {
			return payload.streamCodec().map(CustomPacketPayloadImpl::new, CustomPacketPayloadImpl::getMessage);
		}
		
		public CustomPacketPayload createPayload(M message) {
			return new CustomPacketPayloadImpl(message);
		}
		
		protected boolean canWrite(NetworkEnvironment handlerEnvironment) {
			if (!payload.handlerEnvironment().contains(handlerEnvironment)) {
				LOGGER.error("Failed to write message to channel {} because not handler is defined on the {} environment. Expected {} environment", type.id(), handlerEnvironment, payload.handlerEnvironment());
				return false;
			}
			return true;
		}
		
		protected void handle(M message, NetworkContext context) {
			if (message == null) {
				return;
			}
			final NetworkEnvironment current = context.getEnvironment();
			if (!payload.handlerEnvironment().contains(current)) {
				LOGGER.error("Message {} in channel {} cannot be handled on the {} environment. Expected {} environment", message.getClass(), type.id(), current, payload.handlerEnvironment());
				return;
			}
			try {
				payload.handle(message, context);
			} catch (final Exception ex) {
				LOGGER.error("Failed to handle message {} in channel {}", message.getClass(), type.id(), ex);
			}
		}
		
		private class CustomPacketPayloadImpl implements CustomPacketPayload {
			
			private final M message;
			
			private CustomPacketPayloadImpl(M message) {
				this.message = message;
			}
			
			@Override
			public Type<CustomPacketPayloadImpl> type() {
				return type;
			}
			
			private M getMessage() {
				return message;
			}
		}
	}
	
	protected static abstract class CommonNetworkMessage<M> implements NetworkMessage<M> {
		
		protected final MessagePacketPayload<M> messagePayload;
		
		protected CommonNetworkMessage(MessagePacketPayload<M> messagePayload) {
			this.messagePayload = messagePayload;
		}
		
		protected abstract void sendPacketToPlayer(ServerPlayer player, M message);
		
		protected abstract void sendPacketToConnection(Connection connection, M message);
		
		protected abstract void sendPacketToServer(M message);
		
		@Override
		public CustomPacketPayload packet(M message) {
			return messagePayload.createPayload(message);
		}
		
		@Override
		public final void sendToPlayer(ServerPlayer player, M message) {
			if (messagePayload.canWrite(NetworkEnvironment.CLIENT)) {
				sendPacketToPlayer(player, message);
			}
		}
		
		@Override
		public final void sendToConnection(Connection connection, M message) {
			if (messagePayload.canWrite(NetworkEnvironment.CLIENT)) {
				sendPacketToConnection(connection, message);
			}
		}
		
		@Override
		public final void sendToServer(M message) {
			if (messagePayload.canWrite(NetworkEnvironment.SERVER)) {
				sendPacketToServer(message);
			}
		}
		
	}
	
	protected static abstract class CommonNetworkContext<M> implements NetworkContext {
		
		protected final MessagePacketPayload<M> messagePayload;
		
		protected CommonNetworkContext(MessagePacketPayload<M> messagePayload) {
			this.messagePayload = messagePayload;
		}
		
		protected abstract CompletableFuture<Void> execute(Runnable runnable);
		
		@Override
		public final CompletableFuture<Void> executeOnMainThread(Runnable runnable) {
			return execute(() -> {
				try {
					runnable.run();
				} catch (final Exception ex) {
					LOGGER.error("Failed to handle synchronized message in channel {}", messagePayload.type().id(), ex);
				}
			});
		}
		
	}
}
