package info.u_team.u_team_test.container;

import info.u_team.u_team_core.container.UContainer;
import info.u_team.u_team_test.init.TestContainers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Slot;

public class BasicTileEntityContainer extends UContainer {
	
	public BasicTileEntityContainer(int id, PlayerInventory inventoryPlayer) {
		this(id, inventoryPlayer, new Inventory(18));
	}
	
	public BasicTileEntityContainer(int id, PlayerInventory inventoryPlayer, IInventory inventory) {
		super(TestContainers.type, id);
		
		for (int height = 0; height < 2; height++) {
			for (int width = 0; width < 9; width++) {
				addSlot(new Slot(inventory, width + height * 9, width * 18 + 8, height * 18 + 41));
			}
		}
		
		appendPlayerInventory(inventoryPlayer, 8, 91);
		
	}
}
