package info.u_team.u_team_core.container;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.MessageSyncedContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.*;

/**
 * A synchronized container with custom packets.
 * 
 * @author HyCraftHD
 *
 */
public abstract class USyncedContainer extends UContainer {
	
	/**
	 * This is the server constructor for the container.
	 * 
	 * @param type Container type
	 * @param id Window id
	 */
	public USyncedContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	/**
	 * This is the client constructor for the container.
	 * 
	 * @param type Container type
	 * @param id Window id
	 * @param buffer Initial data (specified with {@link NetworkHooks#openGui(player, containerSupplier,extraDataWriter)})
	 */
	@OnlyIn(Dist.CLIENT)
	public USyncedContainer(ContainerType<?> type, int id, PacketBuffer buffer) {
		super(type, id);
	}
	
	/**
	 * This method is called on the server side. Data can be written to the packet buffer. The data will receive the client
	 * in {@link #handleFromServer(buffer)}.
	 * 
	 * This method is for client -> server sync.
	 * 
	 * @param buffer Packet buffer
	 */
	public void sendToClient(PacketBuffer buffer) {
	}
	
	/**
	 * This method is called on the client side. Data can be read from the packet buffer. The data is from the server method
	 * {@link #sendToClient(buffer)}.
	 * 
	 * This method is for client -> server sync.
	 * 
	 * @param buffer Packet buffer
	 */
	@OnlyIn(Dist.CLIENT)
	public void handleFromServer(PacketBuffer buffer) {
	}
	
	/**
	 * This method is called on the client side. Data can be written to the packet buffer. The data will receive the server
	 * in {@link #handleFromClient(buffer)}.
	 * 
	 * This method is for server -> client sync.
	 * 
	 * @param buffer Packet buffer
	 */
	@OnlyIn(Dist.CLIENT)
	public void sendToServer(PacketBuffer buffer) {
	}
	
	/**
	 * This method is called on the server side. Data can be read from the packet buffer. The data is from the client method
	 * {@link #sendToServer(buffer)}.
	 * 
	 * This method is for server -> client sync.
	 * 
	 * @param buffer Packet buffer
	 */
	public void handleFromClient(PacketBuffer buffer) {
	}
	
	/**
	 * This method will call {@link #sendToClient(buffer)} to get the data and then call
	 * {@link #sendDataToClient(player, buffer)} to send the data.
	 * 
	 * @param player Server player
	 */
	public void sendDataToClient(ServerPlayerEntity player) {
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
	public void sendDataToClient(ServerPlayerEntity player, PacketBuffer buffer) {
		UCoreNetwork.network.send(PacketDistributor.PLAYER.with(() -> player), new MessageSyncedContainer(buffer));
	}
	
	/**
	 * This method will call {@link #sendToServer(buffer)} to get the data and then call {@link #sendDataToServer(buffer)}
	 * to send the data.
	 */
	@OnlyIn(Dist.CLIENT)
	public void sendDataToServer() {
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
	public void sendDataToServer(PacketBuffer buffer) {
		UCoreNetwork.network.send(PacketDistributor.SERVER.noArg(), new MessageSyncedContainer(buffer));
	}
}
