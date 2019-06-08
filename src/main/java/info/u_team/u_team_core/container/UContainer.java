package info.u_team.u_team_core.container;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;

public class UContainer extends Container {
	
	public UContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
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
	
}
