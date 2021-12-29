package info.u_team.u_team_core.menu;

import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

/**
 * Enhanced version of {@link AbstractContainerMenu} with the benefit that the player that opened the container is known
 * and a method is called when the container is opened on the logical server. Furthermore adds some convenience methods
 * to add multiple slots.
 *
 * @author HyCraftHD
 */
public abstract class UAbstractContainerMenu extends AbstractContainerMenu {
	
	private ServerPlayer synchronizerPlayer;
	
	/**
	 * Creates a container menu. Must be implemented by a sub class to be used.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 */
	protected UAbstractContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
	}
	
	/**
	 * Do not call your self. Will be called from an asm hook inside {@link ServerPlayer#initMenu(AbstractContainerMenu)}.
	 * This method sets player that has the container opened. Will be called before
	 * {@link #setSynchronizer(net.minecraft.world.inventory.ContainerSynchronizer)} so that method can be used for
	 * synchronizing already.
	 *
	 * @param player Server player that opened the container
	 */
	public final void setSynchronizerPlayer(ServerPlayer player) {
		synchronizerPlayer = player;
	}
	
	/**
	 * Returns the player that opened the container and should be used for synchronizing purposes.
	 *
	 * @return Server player that opened the container
	 */
	public final ServerPlayer getSynchronizerPlayer() {
		return synchronizerPlayer;
	}
	
	/**
	 * Will be called immediately after the {@link #setSynchronizerPlayer(ServerPlayer)}.
	 *
	 * @param player Server player that openend the container
	 */
	public void initMenu(ServerPlayer player) {
	}
	
	/**
	 * Returns the last slot list that is used to check if a stack has changed since last check. The list should not be
	 * modified manually.
	 * 
	 * @return List with item stacks
	 */
	protected List<ItemStack> getLastSlots() {
		return lastSlots;
	}
	
	/**
	 * This methods adds a player inventory to the container.
	 *
	 * @param playerInventory Player inventory
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addPlayerInventory(Inventory playerInventory, int x, int y) {
		for (var height = 0; height < 4; height++) {
			for (var width = 0; width < 9; width++) {
				if (height == 3) {
					addSlot(new Slot(playerInventory, width, width * 18 + x, height * 18 + 4 + y));
					continue;
				}
				addSlot(new Slot(playerInventory, width + height * 9 + 9, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link Container} to this menu.
	 *
	 * @param container Inventory container
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(Container container, int rows, int columns, int x, int y) {
		addSlots(container, 0, rows, columns, x, y);
	}
	
	/**
	 * This methods can add any {@link Container} to this menu.
	 *
	 * @param container Inventory container
	 * @param function Function to create a slot
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(Container container, SlotContainerFunction function, int rows, int columns, int x, int y) {
		addSlots(container, function, 0, rows, columns, x, y);
	}
	
	/**
	 * This methods can add any {@link Container} to this menu.
	 *
	 * @param container Inventory container
	 * @param startIndex Start index of inventory
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(Container container, int startIndex, int rows, int columns, int x, int y) {
		addSlots(container, Slot::new, startIndex, rows, columns, x, y);
	}
	
	/**
	 * This methods can add any {@link Container} to this menu.
	 *
	 * @param container Inventory container
	 * @param function Function to create a slot
	 * @param startIndex Start index of inventory
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(Container container, SlotContainerFunction function, int startIndex, int rows, int columns, int x, int y) {
		for (var height = 0; height < rows; height++) {
			for (var width = 0; width < columns; width++) {
				addSlot(function.getSlot(container, startIndex + (width + height * columns), width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to this menu.
	 *
	 * @param handler Item inventory handler
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(IItemHandler handler, int rows, int columns, int x, int y) {
		addSlots(handler, 0, rows, columns, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to this menu.
	 *
	 * @param handler Item inventory handler
	 * @param function Function to create a slot
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(IItemHandler handler, SlotHandlerFunction function, int rows, int columns, int x, int y) {
		addSlots(handler, function, 0, rows, columns, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to this menu.
	 *
	 * @param handler Item inventory handler
	 * @param startIndex Start index of inventory
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(IItemHandler handler, int startIndex, int rows, int columns, int x, int y) {
		addSlots(handler, ItemSlot::new, startIndex, rows, columns, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to this menu.
	 *
	 * @param handler Item inventory handler
	 * @param function Function to create a slot
	 * @param startIndex Start index of inventory
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(IItemHandler handler, SlotHandlerFunction function, int startIndex, int rows, int columns, int x, int y) {
		for (var height = 0; height < rows; height++) {
			for (var width = 0; width < columns; width++) {
				addSlot(function.getSlot(handler, startIndex + (width + height * columns), width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * Used as a function to add slots
	 *
	 * @author HyCraftHD
	 */
	@FunctionalInterface
	public static interface SlotContainerFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 *
		 * @param container Inventory container
		 * @param index Index for this inventory
		 * @param x X coordinate
		 * @param y Y coordinate
		 * @return A new slot instance
		 */
		Slot getSlot(Container container, int index, int x, int y);
	}
	
	/**
	 * Used as a function to add slots
	 *
	 * @author HyCraftHD
	 */
	@FunctionalInterface
	public static interface SlotHandlerFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 *
		 * @param handler Item inventory handler
		 * @param index Index for this handler
		 * @param x X coordinate
		 * @param y Y coordinate
		 * @return A new slot instance
		 */
		Slot getSlot(IItemHandler handler, int index, int x, int y);
	}
	
}
