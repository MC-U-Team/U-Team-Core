package info.u_team.u_team_core.api;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.MessageSyncedContainer;
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
 *
 */
public interface ISyncedDataMethods {
	
	/**
	 * This method is called on the server side. Data can be written to the packet buffer. The data will receive the client
	 * in {@link #handleFromServer(buffer)}.
	 * 
	 * This method is for client -> server sync.
	 * 
	 * @param buffer Packet buffer
	 */
	void sendToClient(PacketBuffer buffer);
	
	/**
	 * This method is called on the client side. Data can be read from the packet buffer. The data is from the server method
	 * {@link #sendToClient(buffer)}.
	 * 
	 * This method is for client -> server sync.
	 * 
	 * @param buffer Packet buffer
	 */
	@OnlyIn(Dist.CLIENT)
	void handleFromServer(PacketBuffer buffer);
	
	/**
	 * This method is called on the client side. Data can be written to the packet buffer. The data will receive the server
	 * in {@link #handleFromClient(buffer)}.
	 * 
	 * This method is for server -> client sync.
	 * 
	 * @param buffer Packet buffer
	 */
	@OnlyIn(Dist.CLIENT)
	void sendToServer(PacketBuffer buffer);
	
	/**
	 * This method is called on the server side. Data can be read from the packet buffer. The data is from the client method
	 * {@link #sendToServer(buffer)}.
	 * 
	 * This method is for server -> client sync.
	 * 
	 * @param buffer Packet buffer
	 */
	void handleFromClient(PacketBuffer buffer);
	
	/**
	 * This method will call {@link #sendToClient(buffer)} to get the data and then call
	 * {@link #sendDataToClient(player, buffer)} to send the data.
	 * 
	 * @param player Server player
	 */
	default void sendDataToClient(ServerPlayerEntity player) {
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		sendToClient(buffer);
		sendDataToClient(player, buffer);
	}
	
	/**
	 * This method sends a new {@link MessageSyncedContainer} with a buffer on the uteamcore network channel to a client.
	 * 
	 * @param player Server player
	 * @param buffer Data
	 */
	default void sendDataToClient(ServerPlayerEntity player, PacketBuffer buffer) {
		UCoreNetwork.network.send(PacketDistributor.PLAYER.with(() -> player), new MessageSyncedContainer(buffer));
	}
	
	/**
	 * This method will call {@link #sendToServer(buffer)} to get the data and then call {@link #sendDataToServer(buffer)}
	 * to send the data.
	 */
	@OnlyIn(Dist.CLIENT)
	default void sendDataToServer() {
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		sendToServer(buffer);
		sendDataToServer(buffer);
	}
	
	/**
	 * This method sends a new {@link MessageSyncedContainer} with a buffer on the uteamcore network channel to the server
	 * 
	 * @param buffer Data
	 */
	@OnlyIn(Dist.CLIENT)
	default void sendDataToServer(PacketBuffer buffer) {
		UCoreNetwork.network.send(PacketDistributor.SERVER.noArg(), new MessageSyncedContainer(buffer));
	}
}
