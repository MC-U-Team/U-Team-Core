package info.u_team.u_team_core.api;

import info.u_team.u_team_core.container.USyncedTileEntityContainer;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.*;

/**
 * Implement this in you tile entity when it should sync values to the client side and the over way around.
 * 
 * @author HyCraftHD
 *
 */
public interface ISyncedContainerTileEntity extends INamedContainerProvider {
	
	/**
	 * Collect data here that should be sent to the client side when the container will be opened. The data comes to the
	 * client side in this method: {@link #handleInitialDataBuffer(PacketBuffer)}
	 * 
	 * @param buffer Buffer for 32kb data
	 */
	void sendInitialDataBuffer(PacketBuffer buffer);
	
	/**
	 * The collected data for the initial container opening comes here to the client side. The data is send from the server
	 * side here: {@link #sendInitialDataBuffer(PacketBuffer)}
	 * 
	 * @param buffer Buffer for 32kb data
	 */
	@OnlyIn(Dist.CLIENT)
	void handleInitialDataBuffer(PacketBuffer buffer);
	
	void sendToClient(PacketBuffer buffer);
	
	@OnlyIn(Dist.CLIENT)
	void handleFromServer(PacketBuffer buffer);
	
	@OnlyIn(Dist.CLIENT)
	void sendToServer(PacketBuffer buffer);
	
	void handleFromClient(PacketBuffer buffer);
	
	/**
	 * Override method here so we can make sure the container is an instance of {@link USyncedTileEntityContainer}
	 */
	@Override
	USyncedTileEntityContainer<?> createMenu(int id, PlayerInventory playerInventory, PlayerEntity player);
	
	// // Server -> Client
	//
	// // Write on Server
	// public void writeOnContainerSyncServer(CompoundNBT compound);
	//
	// // Read on Client
	// @OnlyIn(Dist.CLIENT)
	// public void readOnContainerSyncClient(CompoundNBT compound);
	//
	// // Server -> Client
	//
	// // Write on Client
	// @OnlyIn(Dist.CLIENT)
	// public void writeOnContainerSyncClient(CompoundNBT compound);
	//
	// // Read on Server
	// public void readOnContainerSyncServer(CompoundNBT compound);
	//
	// // First data send from server to gui
	// public default void writeOnGuiOpenServer(CompoundNBT compound) {
	// writeOnContainerSyncServer(compound);
	// }
	//
	// public default void syncServerToClient(ServerPlayerEntity player, BlockPos pos) {
	// CompoundNBT compound = new CompoundNBT();
	// writeOnContainerSyncServer(compound);
	// sendMessageToClient(player, pos, compound);
	// }
	//
	// @OnlyIn(Dist.CLIENT)
	// public default void syncClientToServer(BlockPos pos) {
	// CompoundNBT compound = new CompoundNBT();
	// writeOnContainerSyncClient(compound);
	// sendMessageToServer(pos, compound);
	// }
	//
	// // This method access internal stuff. DO NOT OVERWRITE
	// public default void sendMessageToClient(ServerPlayerEntity player, BlockPos pos, CompoundNBT compound) {
	// UCoreNetwork.network.send(PacketDistributor.PLAYER.with(() -> player), new MessageSyncedContainer(pos, compound));
	// }
	//
	// // This method access internal stuff. DO NOT OVERWRITE
	// @OnlyIn(Dist.CLIENT)
	// public default void sendMessageToServer(BlockPos pos, CompoundNBT compound) {
	// UCoreNetwork.network.sendToServer(new MessageSyncedContainer(pos, compound));
	// }
	
}
