package info.u_team.u_team_core.api.sync;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.*;

/**
 * Implement this in you tile entity when it should sync values when a player opens the container
 * 
 * @author HyCraftHD
 */
public interface IInitSyncedTileEntity extends INamedContainerProvider {
	
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
	
	@Override
	Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player);
	
}
