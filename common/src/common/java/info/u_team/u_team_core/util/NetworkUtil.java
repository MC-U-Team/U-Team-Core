package info.u_team.u_team_core.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.server.level.ServerPlayer;

public class NetworkUtil {
	
	public static ClientboundCustomPayloadPacket createClientBoundPacket(CustomPacketPayload payload) {
		return new ClientboundCustomPayloadPacket(payload);
	}
	
	public static Packet<?> createClientBoundBundlePacket(CustomPacketPayload mainPayload, CustomPacketPayload... payloads) {
		if (payloads.length == 0) {
			return createClientBoundPacket(mainPayload);
		} else {
			final List<Packet<? super ClientGamePacketListener>> packets = new ArrayList<>();
			packets.add(createClientBoundPacket(mainPayload));
			for (final CustomPacketPayload payload : payloads) {
				packets.add(createClientBoundPacket(payload));
			}
			return new ClientboundBundlePacket(packets);
		}
	}
	
	public static ServerboundCustomPayloadPacket createServerBoundPacket(CustomPacketPayload payload) {
		return new ServerboundCustomPayloadPacket(payload);
	}
	
	public static void sendToPlayer(ServerPlayer player, CustomPacketPayload mainPayload, CustomPacketPayload... payloads) {
		sendToPlayer(player, createClientBoundBundlePacket(mainPayload, payloads));
	}
	
	public static void sendToPlayer(ServerPlayer player, Packet<?> packet) {
		player.connection.send(packet);
	}
	
	public static void sendToConnection(Connection connection, CustomPacketPayload mainPayload, CustomPacketPayload... payloads) {
		sendToConnection(connection, createClientBoundBundlePacket(mainPayload, payloads));
	}
	
	public static void sendToConnection(Connection connection, Packet<?> packet) {
		connection.send(packet);
	}
	
	public static void sendToServer(CustomPacketPayload mainPayload, CustomPacketPayload... payloads) {
		sendToServer(createServerBoundPacket(mainPayload));
		for (final CustomPacketPayload payload : payloads) {
			sendToServer(createServerBoundPacket(payload));
		}
	}
	
	public static void sendToServer(Packet<?> packet) {
		Minecraft.getInstance().getConnection().send(packet);
	}
	
}
