package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.UContainerTileEntity;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;

public class ContainerTileEntity extends UContainerTileEntity {
	
	public ContainerTileEntity(InventoryPlayer inventoryPlayer, TileEntityTileEntity tileentity) {
		super(tileentity);
		
		IInventory inventoryBasic = tileentity;
		
		for (int height = 0; height < 2; height++) {
			for (int width = 0; width < 9; width++) {
				addSlot(new Slot(inventoryBasic, width + height * 9, width * 18 + 8, height * 18 + 41));
			}
		}
		
		appendPlayerInventory(inventoryPlayer, 8, 91);
		
	}
}
