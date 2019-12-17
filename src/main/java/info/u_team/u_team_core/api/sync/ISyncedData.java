package info.u_team.u_team_core.api.sync;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.SyncedContainerMessage;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.PacketDistributor;

/**
 * Sync methods for containers and tile entities. This is kind of a utility interface to use commonly shared code and
 * generalize the methods.
 * 
 * @author HyCraftHD
 */
@Deprecated
public interface ISyncedData {
	
	/**
	 * This method is called on the server side. Data can be written to the packet buffer. The data will receive the client
	 * in {@link #handleFromServer(PacketBuffer)}. This method is for client -> server sync.
	 * 
	 * @param buffer Packet buffer
	 */
	void sendToClient(PacketBuffer buffer);
	
	/**
	 * This method is called on the client side. Data can be read from the packet buffer. The data is from the server method
	 * {@link #sendToClient(PacketBuffer)}. This method is for client -> server sync.
	 * 
	 * @param buffer Packet buffer
	 */
	@OnlyIn(Dist.CLIENT)
	void handleFromServer(PacketBuffer buffer);
	
	/**
	 * This method is called on the client side. Data can be written to the packet buffer. The data will receive the server
	 * in {@link #handleFromClient(PacketBuffer)}. This method is for server -> client sync.
	 * 
	 * @param buffer Packet buffer
	 */
	@OnlyIn(Dist.CLIENT)
	void sendToServer(PacketBuffer buffer);
	
	/**
	 * This method is called on the server side. Data can be read from the packet buffer. The data is from the client method
	 * {@link #sendToServer(PacketBuffer)}. This method is for server -> client sync.
	 * 
	 * @param buffer Packet buffer
	 */
	void handleFromClient(PacketBuffer buffer);
	
	/**
	 * This method will call {@link #sendToClient(PacketBuffer)} to get the data and then call
	 * {@link #sendDataToClient(ServerPlayerEntity, PacketBuffer)} to send the data.
	 * 
	 * @param player Server player
	 */
	default void sendDataToClient(ServerPlayerEntity player) {
		final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		sendToClient(buffer);
		sendDataToClient(player, buffer);
	}
	
	/**
	 * This method sends a new {@link SyncedContainerMessage} with a buffer on the uteamcore network channel to a client.
	 * 
	 * @param player Server player
	 * @param buffer Data
	 */
	default void sendDataToClient(ServerPlayerEntity player, PacketBuffer buffer) {
		UCoreNetwork.NETWORK.send(PacketDistributor.PLAYER.with(() -> player), new SyncedContainerMessage(buffer));
	}
	
	/**
	 * This method will call {@link #sendToServer(PacketBuffer)} to get the data and then call
	 * {@link #sendDataToServer(PacketBuffer)} to send the data.
	 */
	@OnlyIn(Dist.CLIENT)
	default void sendDataToServer() {
		final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		sendToServer(buffer);
		sendDataToServer(buffer);
	}
	
	/**
	 * This method sends a new {@link SyncedContainerMessage} with a buffer on the uteamcore network channel to the server
	 * 
	 * @param buffer Data
	 */
	@OnlyIn(Dist.CLIENT)
	default void sendDataToServer(PacketBuffer buffer) {
		UCoreNetwork.NETWORK.send(PacketDistributor.SERVER.noArg(), new SyncedContainerMessage(buffer));
	}
}
