package info.u_team.u_team_core.api.block;

import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
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
	 * Collect data here that should be sent to the client side when the menu will be opened. The method will only be called
	 * automatically if
	 * {@link EntityBlockProvider#openMenu(net.minecraft.world.level.Level, net.minecraft.core.BlockPos, Player, boolean)}
	 * is used for opening the menu. <br>
	 * The data will be received on the client side in this method: {@link #handleInitialMenuDataFromServer(FriendlyByteBuf)}
	 *
	 * @param byteBuf Data to be send to the client
	 */
	void sendInitialMenuDataToClient(FriendlyByteBuf byteBuf);
	
	/**
	 * The collected data for the initial menu opening is received here on the client side. The method will only be called
	 * if {@link UBlockEntityContainerMenu} is used as a base class for the menu or an equivalent implementation is used.
	 * <br>
	 * The data is sent from the server side here: {@link #sendInitialMenuDataToClient(FriendlyByteBuf)}
	 *
	 * @param byteBuf Received data from the server
	 */
	void handleInitialMenuDataFromServer(FriendlyByteBuf byteBuf);
	
	/**
	 * Used to create a menu on the server side
	 */
	@Override
	AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player);
	
}
