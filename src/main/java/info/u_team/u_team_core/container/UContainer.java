package info.u_team.u_team_core.container;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

/**
 * Container API<br>
 * -> Basic Container
 * 
 * @date 21.10.2017
 * @author HyCraftHD
 */
public class UContainer extends Container {
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	public void appendPlayerInventory(InventoryPlayer inventory, int x, int y) {
		for (int height = 0; height < 4; height++) {
			for (int width = 0; width < 9; width++) {
				if (height == 3) {
					addSlotToContainer(new Slot(inventory, width, width * 18 + x, height * 18 + 4 + y));
					continue;
				}
				addSlotToContainer(new Slot(inventory, width + height * 9 + 9, width * 18 + x, height * 18 + y));
			}
		}
	}
	
}
