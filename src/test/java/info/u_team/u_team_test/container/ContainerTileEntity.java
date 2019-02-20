package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_test.tileentity.TileEntityTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;

public class ContainerTileEntity extends UContainer {
	
	public ContainerTileEntity(InventoryPlayer inventoryPlayer, TileEntityTileEntity tileentity) {
		
		IInventory inventoryBasic = tileentity;
		
		System.out.println(inventorySlots);
		System.out.println(inventoryItemStacks);
		
		System.out.println(inventorySlots.size());
		System.out.println(inventoryItemStacks.size());
		
		for (int height = 0; height < 2; height++) {
			for (int width = 0; width < 9; width++) {
				addSlot(new Slot(inventoryBasic, width + height * 9, width * 18 + 5, height * 18 + 5));
			}
		}
		
		appendPlayerInventory(inventoryPlayer, 5, 80);
		
	}
	
}
