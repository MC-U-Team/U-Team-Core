package info.u_team.u_team_core.container;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.MessageSyncedContainer;
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
	
	public void sendToClient(PacketBuffer buffer) {
	}
	
	@OnlyIn(Dist.CLIENT)
	public void handleFromServer(PacketBuffer buffer) {
	}
	
	@OnlyIn(Dist.CLIENT)
	public void sendToServer(PacketBuffer buffer) {
	}
	
	public void handleFromClient(PacketBuffer buffer) {
	}
	
	public void sendDataToClient(ServerPlayerEntity player, PacketBuffer buffer) {
//		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
//		sendToClient(buffer);
		UCoreNetwork.network.send(PacketDistributor.PLAYER.with(() -> player), new MessageSyncedContainer(buffer));
	}
	
	@OnlyIn(Dist.CLIENT)
	public void sendDataToServer(PacketBuffer buffer) {
//		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
//		sendToServer(buffer);
		UCoreNetwork.network.send(PacketDistributor.SERVER.noArg(), new MessageSyncedContainer(buffer));
	}
}
