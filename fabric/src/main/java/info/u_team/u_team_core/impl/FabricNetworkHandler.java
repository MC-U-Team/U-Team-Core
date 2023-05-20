package info.u_team.u_team_core.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import info.u_team.u_team_core.util.CastUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;

public class FabricNetworkHandler implements NetworkHandler {
	
	private final ResourceLocation channel;
	private final Map<Class<?>, MessagePacket<?>> messages;
	
	FabricNetworkHandler(ResourceLocation channel) {
		this.channel = channel;
		messages = new HashMap<>();
	}
	
	@Override
	public <M> void registerMessage(int index, Class<M> clazz, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, NetworkContext> messageConsumer, Optional<NetworkEnvironment> handlerEnvironment) {
		final ResourceLocation location = channel.withSuffix("/" + index);
		MessagePacket<?> oldPacket = messages.put(clazz, new MessagePacket<>(location, encoder, handlerEnvironment));
		if (oldPacket != null) {
			throw new IllegalArgumentException("Packet class " + clazz + " was already registered");
		}
		
		if (validNetworkEnvironment(NetworkEnvironment.SERVER, handlerEnvironment)) {
			// Register client -> server handler
			ServerPlayNetworking.registerGlobalReceiver(location, (server, player, packetListener, byteBuf, responseSender) -> {
				messageConsumer.accept(decodeMessage(decoder, byteBuf), new FabricNetworkContext(NetworkEnvironment.SERVER, player, server));
			});
		}
		
		if (validNetworkEnvironment(NetworkEnvironment.CLIENT, handlerEnvironment)) {
			// Register server -> client handler
			runWhen(EnvType.CLIENT, () -> () -> Client.registerReceiver(this, location, decoder, messageConsumer));
		}
	}
	
	@Override
	public <M> void sendToPlayer(ServerPlayer player, M message) {
		final EncodedMessage encodedMessage = encodeMessage(message, NetworkEnvironment.CLIENT);
		ServerPlayNetworking.send(player, encodedMessage.location, encodedMessage.byteBuf);
	}
	
	@Override
	public <M> void sendToServer(M message) {
		runWhen(EnvType.CLIENT, () -> () -> Client.send(this, message));
	}
	
	private <M> EncodedMessage encodeMessage(M message, NetworkEnvironment expectedHandler) {
		final MessagePacket<M> packet = CastUtil.uncheckedCast(messages.get(message.getClass()));
		if (packet == null) {
			throw new IllegalArgumentException("Message " + message.getClass() + " was not registred");
		}
		if (!validNetworkEnvironment(expectedHandler, packet.handlerEnvironment)) {
			throw new IllegalArgumentException("Message " + message.getClass() + " cannot be used to send to " + expectedHandler);
		}
		final FriendlyByteBuf byteBuf = PacketByteBufs.create();
		packet.encoder.accept(message, byteBuf);
		return new EncodedMessage(packet.location, byteBuf);
	}
	
	private <M> M decodeMessage(Function<FriendlyByteBuf, M> decoder, FriendlyByteBuf byteBuf) {
		return decoder.apply(byteBuf);
	}
	
	private boolean validNetworkEnvironment(NetworkEnvironment expected, Optional<NetworkEnvironment> handlerEnvironment) {
		final NetworkEnvironment environment = handlerEnvironment.orElse(null);
		return environment == null || environment == expected;
	}
	
	@Environment(EnvType.CLIENT)
	private class Client {
		
		public static <M> void send(FabricNetworkHandler handler, M message) {
			final EncodedMessage encodedMessage = handler.encodeMessage(message, NetworkEnvironment.SERVER);
			ClientPlayNetworking.send(encodedMessage.location, encodedMessage.byteBuf);
		}
		
		public static <M> void registerReceiver(FabricNetworkHandler handler, ResourceLocation location, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, NetworkContext> messageConsumer) {
			ClientPlayNetworking.registerGlobalReceiver(location, (client, packetListener, byteBuf, responseSender) -> {
				messageConsumer.accept(handler.decodeMessage(decoder, byteBuf), new FabricNetworkContext(NetworkEnvironment.CLIENT, client.player, client));
			});
		}
	}
	
	private void runWhen(EnvType type, Supplier<Runnable> supplier) {
		if (type == FabricLoader.getInstance().getEnvironmentType()) {
			supplier.get().run();
		}
	}
	
	private record MessagePacket<M> (ResourceLocation location, BiConsumer<M, FriendlyByteBuf> encoder, Optional<NetworkEnvironment> handlerEnvironment) {
	}
	
	private record EncodedMessage(ResourceLocation location, FriendlyByteBuf byteBuf) {
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
		public NetworkHandler create(ResourceLocation location) {
			return new FabricNetworkHandler(location);
		}
	}
}
