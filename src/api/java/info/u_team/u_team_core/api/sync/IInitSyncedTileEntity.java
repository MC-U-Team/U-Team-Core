package info.u_team.u_team_core.api.sync;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Implement this in you tile entity when it should sync values when a player opens the container
 * 
 * @author HyCraftHD
 */
public interface IInitSyncedTileEntity extends MenuProvider {
	
	/**
	 * Collect data here that should be sent to the client side when the container will be opened. The data comes to the
	 * client side in this method: {@link #handleInitialDataBuffer(PacketBuffer)}
	 * 
	 * @param buffer Buffer for 32kb data
	 */
	void sendInitialDataBuffer(FriendlyByteBuf buffer);
	
	/**
	 * The collected data for the initial container opening comes here to the client side. The data is send from the server
	 * side here: {@link #sendInitialDataBuffer(PacketBuffer)}
	 * 
	 * @param buffer Buffer for 32kb data
	 */
	@OnlyIn(Dist.CLIENT)
	void handleInitialDataBuffer(FriendlyByteBuf buffer);
	
	@Override
	AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player);
	
}
