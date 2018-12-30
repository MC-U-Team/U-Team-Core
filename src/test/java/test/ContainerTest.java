package test;

import info.u_team.u_team_core.container.UContainerTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerTest extends UContainerTileEntity {
	
	public ContainerTest(InventoryPlayer playerinv, TileEntity tileentity) {
		super(tileentity);
		appendPlayerInventory(playerinv, 10, 80);
	}
	
}
