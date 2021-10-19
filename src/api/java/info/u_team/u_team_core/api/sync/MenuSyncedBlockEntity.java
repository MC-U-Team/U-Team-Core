package info.u_team.u_team_core.api.sync;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Implement this in you block entity when it should synchronize initial values when a player opens the container
 *
 * @author HyCraftHD
 */
public interface MenuSyncedBlockEntity extends MenuProvider {
	
	/**
	 * Collect data here that should be sent to the client side when the container will be opened. The data comes to the
	 * client side in this method: {@link #handleInitialDataFromServer(PacketBuffer)}
	 *
	 * @param byteBuf Data to be send to the client
	 */
	void sendInitialDataToClient(FriendlyByteBuf byteBuf);
	
	/**
	 * The collected data for the initial container opening comes here to the client side. The data is send from the server
	 * side here: {@link #sendInitialDataToClient(PacketBuffer)}
	 *
	 * @param byteBuf Received data from the server
	 */
	void handleInitialDataFromServer(FriendlyByteBuf byteBuf);
	
	@Override
	AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player);
	
}
