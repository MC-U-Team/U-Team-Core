package test;

import info.u_team.u_team_core.container.UContainerTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerTestBuggy extends UContainerTileEntity {
	
	public ContainerTestBuggy(InventoryPlayer playerinv, TileEntity tileentity) {
		super(tileentity);
		appendPlayerInventory(playerinv, 10, 80);
	}
	
}
