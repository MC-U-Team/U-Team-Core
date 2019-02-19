package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.UContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerTileEntity extends UContainer {
	
	public ContainerTileEntity(InventoryPlayer inventoryPlayer) {
		appendPlayerInventory(inventoryPlayer, 5, 5);
	}
	
}
