package info.u_team.u_team_core.impl.common;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.api.network.NetworkMessage;
import info.u_team.u_team_core.api.network.NetworkPayload;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public abstract class CommonNetworkHandler implements NetworkHandler {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger("NetworkHandler");
	
	protected final ResourceLocation channel;
	protected final int protocolVersion;
	
	protected final Set<MessageNetworkPayload<?>> messages;
	
	protected CommonNetworkHandler(ResourceLocation channel, int protocolVersion) {
		this.channel = channel;
		this.protocolVersion = protocolVersion;
		messages = new HashSet<>();
	}
	
	protected abstract <M> NetworkMessage<M> createMessage(MessageNetworkPayload<M> messagePayload);
	
	protected <M> MessageNetworkPayload<M> createMessageNetworkPayload(ResourceLocation messageId, NetworkPayload<M> payload) {
		return new MessageNetworkPayload<>(messageId, payload);
	}
	
	@Override
	public <M> NetworkMessage<M> register(String id, NetworkPayload<M> payload) {
		final ResourceLocation messageId = channel.withSuffix("/" + id);
		
		if (payload.getHandlerEnvironment().isEmpty()) {
			throw new IllegalArgumentException("Handler environment cannot be empty for message id " + messageId);
		}
		
		final MessageNetworkPayload<M> messagePayload = createMessageNetworkPayload(messageId, payload);
		
		if (!messages.add(messagePayload)) {
			throw new IllegalArgumentException("Duplicate message id " + messageId);
		}
		
		return createMessage(messagePayload);
	}
	
	@Override
	public ResourceLocation getChannel() {
		return channel;
	}
	
	@Override
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	protected static class MessageNetworkPayload<M> {
		
		private final ResourceLocation messageId;
		private final NetworkPayload<M> payload;
		
		protected MessageNetworkPayload(ResourceLocation messageId, NetworkPayload<M> payload) {
			this.messageId = messageId;
			this.payload = payload;
		}
		
		public ResourceLocation getMessageId() {
			return messageId;
		}
		
		public NetworkPayload<M> getPayload() {
			return payload;
		}
		
		public boolean canWrite(NetworkEnvironment handlerEnvironment) {
			if (!payload.getHandlerEnvironment().contains(handlerEnvironment)) {
				LOGGER.error("Failed to write message to channel {} because it cannot be sent to the {} environment. Expected {}", messageId, handlerEnvironment, payload.getHandlerEnvironment());
				return false;
			}
			return true;
		}
		
		public void write(M message, FriendlyByteBuf buffer) {
			try {
				payload.write(message, buffer);
			} catch (final Exception ex) {
				LOGGER.error("Failed to write message {} to channel {}", message.getClass(), messageId, ex);
			}
		}
		
		public M read(FriendlyByteBuf buffer) {
			try {
				return payload.read(buffer);
			} catch (final Exception ex) {
				LOGGER.error("Failed to read message in channel {}", messageId, ex);
				return null;
			}
		}
		
		public void handle(M message, NetworkContext context) {
			final NetworkEnvironment current = context.getEnvironment();
			if (!payload.getHandlerEnvironment().contains(current)) {
				LOGGER.error("Message {} in channel {} cannot be handled on the {} environment. Expected {}", message.getClass(), messageId, current, payload.getHandlerEnvironment());
				return;
			}
			try {
				payload.handle(message, context);
			} catch (final Exception ex) {
				LOGGER.error("Failed to handle message {} in channel {}", message.getClass(), messageId, ex);
			}
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(messageId);
		}
		
		@Override
		public boolean equals(Object object) {
			if (this == object)
				return true;
			if (object == null)
				return false;
			if (getClass() != object.getClass())
				return false;
			final MessageNetworkPayload<?> other = (MessageNetworkPayload<?>) object;
			return Objects.equals(messageId, other.messageId);
		}
		
	}
	
	protected static abstract class CommonNetworkMessage<M> implements NetworkMessage<M> {
		
		protected final MessageNetworkPayload<M> messagePayload;
		
		protected CommonNetworkMessage(MessageNetworkPayload<M> messagePayload) {
			this.messagePayload = messagePayload;
		}
		
		protected abstract void sendPacketToPlayer(ServerPlayer player, M message);
		
		protected abstract void sendPacketToConnection(Connection connection, M message);
		
		protected abstract void sendPacketToServer(M message);
		
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
	
	protected static abstract class CommonNetworkContext implements NetworkContext {
		
		protected final ResourceLocation messageId;
		
		public CommonNetworkContext(ResourceLocation messageId) {
			this.messageId = messageId;
		}
		
		protected abstract CompletableFuture<Void> execute(Runnable runnable);
		
		@Override
		public final CompletableFuture<Void> executeOnMainThread(Runnable runnable) {
			return execute(runnable).exceptionally(ex -> {
				LOGGER.error("Failed to handle synchronized message in channel {}", messageId, ex);
				return null;
			});
		}
		
	}
}
