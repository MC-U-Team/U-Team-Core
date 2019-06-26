package info.u_team.u_team_core.container;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraftforge.items.*;

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
		appendInventory(inventory, Slot::new, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 * 
	 * @param inventory Some inventory
	 * @param function Function to create a slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void appendInventory(IInventory inventory, SlotInventoryFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (int height = 0; height < inventoryHeight; height++) {
			for (int width = 0; width < inventoryWidth; width++) {
				addSlot(function.getSlot(inventory, width + height * inventoryWidth, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns).
	 * 
	 * @param handler Some item handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void appendInventory(IItemHandler handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, SlotItemHandler::new, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 * 
	 * @param handler Some item handler
	 * @param function Function to create a slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	public void appendInventory(IItemHandler handler, SlotHandlerFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (int height = 0; height < inventoryHeight; height++) {
			for (int width = 0; width < inventoryWidth; width++) {
				addSlot(function.getSlot(handler, width + height * inventoryWidth, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * Used as a function to customize slots with the append methods
	 * 
	 * @author HyCraftHD
	 *
	 */
	@FunctionalInterface
	public static interface SlotInventoryFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 * 
		 * @param inventory An inventory
		 * @param index Index for this inventory
		 * @param xPosition x coordinate
		 * @param yPosition y coordinate
		 * @return
		 */
		Slot getSlot(IInventory inventory, int index, int xPosition, int yPosition);
	}
	
	/**
	 * Used as a function to customize slots with the append methods
	 * 
	 * @author HyCraftHD
	 *
	 */
	@FunctionalInterface
	public static interface SlotHandlerFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 * 
		 * @param itemHandler An item handler
		 * @param index Index for this item handler
		 * @param xPosition x coordinate
		 * @param yPosition y coordinate
		 * @return
		 */
		Slot getSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition);
	}
}
