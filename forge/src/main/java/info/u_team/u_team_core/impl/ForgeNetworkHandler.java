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
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class ForgeNetworkHandler implements NetworkHandler {
	
	private final int protocolVersion;
	
	private Predicate<Integer> clientAcceptedVersions;
	private Predicate<Integer> serverAcceptedVersions;
	
	private final SimpleChannel network;
	
	ForgeNetworkHandler(int protocolVersion, ResourceLocation channel) {
		this.protocolVersion = protocolVersion;
		clientAcceptedVersions = received -> received == protocolVersion;
		serverAcceptedVersions = received -> received == protocolVersion;
		
		network = ChannelBuilder.named(channel).networkProtocolVersion(protocolVersion).clientAcceptedVersions((status, version) -> clientAcceptedVersions.test(version)).serverAcceptedVersions((status, version) -> serverAcceptedVersions.test(version)).simpleChannel();
	}
	
	@Override
	public <M> void registerMessage(int index, Class<M> clazz, BiConsumer<M, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, M> decoder, BiConsumer<M, NetworkContext> messageConsumer, Optional<NetworkEnvironment> handlerEnvironment) {
		network.messageBuilder(clazz, index, handlerEnvironment.map(environment -> {
			return switch (environment) {
			case CLIENT -> NetworkDirection.PLAY_TO_CLIENT;
			case SERVER -> NetworkDirection.PLAY_TO_SERVER;
			};
		}).orElse(null)).encoder(encoder).decoder(decoder).consumerNetworkThread((message, context) -> {
			messageConsumer.accept(message, new ForgeNetworkContext(context));
			context.setPacketHandled(true);
		}).add();
	}
	
	@Override
	public <M> void sendToPlayer(ServerPlayer player, M message) {
		network.send(message, PacketDistributor.PLAYER.with(player));
	}
	
	@Override
	public <M> void sendToServer(M message) {
		network.send(message, PacketDistributor.SERVER.noArg());
	}
	
	@Override
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	@Override
	public void setProtocolAcceptor(Predicate<Integer> clientAcceptedVersions, Predicate<Integer> serverAcceptedVersions) {
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
		public void executeOnMainThread(Runnable runnable) {
			context.enqueueWork(runnable);
		}
	}
	
	public static class Factory implements NetworkHandler.Factory {
		
		@Override
		public NetworkHandler create(int protocolVersion, ResourceLocation location) {
			return new ForgeNetworkHandler(protocolVersion, location);
		}
	}
}
