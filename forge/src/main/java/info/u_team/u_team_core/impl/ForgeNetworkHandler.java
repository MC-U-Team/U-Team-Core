package info.u_team.u_team_core.impl;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ForgeNetworkHandler implements NetworkHandler {
	
	private final String protocolVersion;
	private final SimpleChannel network;
	
	private Predicate<String> clientAcceptedVersions;
	private Predicate<String> serverAcceptedVersions;
	
	ForgeNetworkHandler(String protocolVersion, ResourceLocation channel) {
		this.protocolVersion = protocolVersion;
		network = NetworkRegistry.newSimpleChannel(channel, () -> protocolVersion, version -> clientAcceptedVersions.test(version), version -> serverAcceptedVersions.test(version));
		clientAcceptedVersions = protocolVersion::equals;
		serverAcceptedVersions = protocolVersion::equals;
	}
	
	@Override
	public <M> void registerMessage(int index, Class<M> clazz, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, NetworkContext> messageConsumer, Optional<NetworkEnvironment> handlerEnvironment) {
		network.registerMessage(index, clazz, encoder, decoder, (message, contextSupplier) -> {
			final NetworkEvent.Context context = contextSupplier.get();
			messageConsumer.accept(message, new ForgeNetworkContext(context));
			context.setPacketHandled(true);
		}, handlerEnvironment.map(environment -> {
			return switch (environment) {
			case CLIENT -> NetworkDirection.PLAY_TO_CLIENT;
			case SERVER -> NetworkDirection.PLAY_TO_SERVER;
			};
		}));
	}
	
	@Override
	public <M> void sendToPlayer(ServerPlayer player, M message) {
		network.send(PacketDistributor.PLAYER.with(() -> player), message);
	}
	
	@Override
	public <M> void sendToServer(M message) {
		network.send(PacketDistributor.SERVER.noArg(), message);
	}
	
	@Override
	public String getProtocolVersion() {
		return protocolVersion;
	}
	
	public void setProtocolAcceptor(Predicate<String> clientAcceptedVersions, Predicate<String> serverAcceptedVersions) {
		this.clientAcceptedVersions = clientAcceptedVersions;
		this.serverAcceptedVersions = serverAcceptedVersions;
	}
	
	@OnlyIn(Dist.CLIENT)
	private class Client {
		
		@OnlyIn(Dist.CLIENT)
		private static Player getClientPlayer() {
			return Minecraft.getInstance().player;
		}
	}
	
	public static class ForgeNetworkContext implements NetworkContext {
		
		private final NetworkEvent.Context context;
		
		ForgeNetworkContext(NetworkEvent.Context context) {
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
		public void executeOnMainThread(Runnable runnable) {
			context.enqueueWork(runnable);
		}
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(String protocolVersion, ResourceLocation location) {
			return new ForgeNetworkHandler(protocolVersion, location);
		}
	}
}
