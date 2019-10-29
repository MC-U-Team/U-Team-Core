package info.u_team.u_team_core.api.sync;

import info.u_team.u_team_core.container.USyncedTileEntityContainer;
import net.minecraft.entity.player.*;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.*;

/**
 * Implement this in you tile entity when it should sync values to the client side and the over way around.
 * 
 * @author HyCraftHD
 */
@Deprecated
public interface ISyncedTileEntity extends IInitSyncedTileEntity, ISyncedData {
	
	default void sendInitialDataBuffer(PacketBuffer buffer) {
		sendToClient(buffer);
	}
	
	@OnlyIn(Dist.CLIENT)
	default void handleInitialDataBuffer(PacketBuffer buffer) {
		handleFromServer(buffer);
	}
	
	/**
	 * Override method here so we can make sure the container is an instance of {@link USyncedTileEntityContainer}
	 */
	@Override
	USyncedTileEntityContainer<?> createMenu(int id, PlayerInventory playerInventory, PlayerEntity player);
	
	// Override here so these methods must not be implemented if not used
	
	@Override
	default void sendToClient(PacketBuffer buffer) {
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	default void handleFromServer(PacketBuffer buffer) {
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	default void sendToServer(PacketBuffer buffer) {
	}
	
	@Override
	default void handleFromClient(PacketBuffer buffer) {
	}
	
}
