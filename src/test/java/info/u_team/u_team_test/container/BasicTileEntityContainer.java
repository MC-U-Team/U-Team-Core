package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.USyncedTileEntityContainer;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicTileEntityTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;

public class BasicTileEntityContainer extends USyncedTileEntityContainer<BasicTileEntityTileEntity> {
	
	public BasicTileEntityContainer(int id, PlayerInventory playerInventory, BasicTileEntityTileEntity tileEntity) {
		super(TestContainers.BASIC, id, playerInventory, tileEntity);
	}
	
	public BasicTileEntityContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(TestContainers.BASIC, id, playerInventory, buffer);
	}
	
	@Override
	protected void init(boolean server) {
		final IInventory inventory = getInventoryOnDist(server, tileEntity);
		
		for (int height = 0; height < 2; height++) {
			for (int width = 0; width < 9; width++) {
				addSlot(new Slot(inventory, width + height * 9, width * 18 + 8, height * 18 + 41));
			}
		}
		
		appendPlayerInventory(playerInventory, 8, 91);
	}
}
