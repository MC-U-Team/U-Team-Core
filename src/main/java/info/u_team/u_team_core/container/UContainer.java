package info.u_team.u_team_core.container;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;

/**
 * A basic container
 * 
 * @author HyCraftHD
 *
 */
public abstract class UContainer extends Container {
	
	public UContainer(ContainerType<?> type, int id) {
		super(type, id);
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return true;
	}
	
	/**
	 * This methods adds a player inventory to the container.
	 * 
	 * @param playerInventory Player inventory
	 * @param x Start x
	 * @param y Start y
	 */
	public void appendPlayerInventory(PlayerInventory playerInventory, int x, int y) {
		for (int height = 0; height < 4; height++) {
			for (int width = 0; width < 9; width++) {
				if (height == 3) {
					addSlot(new Slot(playerInventory, width, width * 18 + x, height * 18 + 4 + y));
					continue;
				}
				addSlot(new Slot(playerInventory, width + height * 9 + 9, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns).
	 * 
	 * @param inventory Some inventory
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void appendInventory(IInventory inventory, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (int height = 0; height < inventoryHeight; height++) {
			for (int width = 0; width < inventoryWidth; width++) {
				addSlot(new Slot(inventory, width + height * inventoryWidth, width * 18 + x, height * 18 + y));
			}
		}
	}
}
