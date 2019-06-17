package info.u_team.u_team_core.api.sync;

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
public interface ISyncedTileEntity extends INamedContainerProvider, ISyncedDataMethods {
	
	/**
	 * Collect data here that should be sent to the client side when the container will be opened. The data comes to the
	 * client side in this method: {@link #handleInitialDataBuffer(PacketBuffer)}
	 * 
	 * @param buffer Buffer for 32kb data
	 */
	default void sendInitialDataBuffer(PacketBuffer buffer) {
		sendToClient(buffer);
	}
	
	/**
	 * The collected data for the initial container opening comes here to the client side. The data is send from the server
	 * side here: {@link #sendInitialDataBuffer(PacketBuffer)}
	 * 
	 * @param buffer Buffer for 32kb data
	 */
	@OnlyIn(Dist.CLIENT)
	default void handleInitialDataBuffer(PacketBuffer buffer) {
		handleFromServer(buffer);
	}
	
	/**
	 * Override method here so we can make sure the container is an instance of {@link USyncedTileEntityContainer}
	 */
	@Override
	USyncedTileEntityContainer<?> createMenu(int id, PlayerInventory playerInventory, PlayerEntity player);
	
}
