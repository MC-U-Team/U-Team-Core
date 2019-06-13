package info.u_team.u_team_test.container;

import info.u_team.u_team_test.init.TestContainers;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.*;

public class ContainerTileEntity extends Container {
	
	public ContainerTileEntity(int id, PlayerInventory inventoryPlayer) {
		this(id, inventoryPlayer, new Inventory(18));
	}
	
	public ContainerTileEntity(int id, PlayerInventory inventoryPlayer, IInventory tileentity) {
		super(TestContainers.type, id);
		
		IInventory inventoryBasic = tileentity;
		
		for (int height = 0; height < 2; height++) {
			for (int width = 0; width < 9; width++) {
				addSlot(new Slot(inventoryBasic, width + height * 9, width * 18 + 8, height * 18 + 41));
			}
		}
		
		appendPlayerInventory(inventoryPlayer, 8, 91);
		
	}
	
	public void appendPlayerInventory(PlayerInventory inventory, int x, int y) {
		for (int height = 0; height < 4; height++) {
			for (int width = 0; width < 9; width++) {
				if (height == 3) {
					addSlot(new Slot(inventory, width, width * 18 + x, height * 18 + 4 + y));
					continue;
				}
				addSlot(new Slot(inventory, width + height * 9 + 9, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}
}
