package info.u_team.u_team_core.menu;

import java.util.List;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

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
	 * @param player Server player that opened the container
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
	 * This methods can add any {@link ItemSlotCreator} to this menu.
	 *
	 * @param slotCreator Slot creator
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(ItemSlotCreator slotCreator, int rows, int columns, int x, int y) {
		addSlots(slotCreator, 0, rows, columns, x, y);
	}
	
	/**
	 * This methods can add any {@link ItemSlotCreator} to this menu.
	 *
	 * @param slotCreator Slot creator
	 * @param startIndex Start index of inventory
	 * @param rows Slot rows
	 * @param columns Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void addSlots(ItemSlotCreator slotCreator, int startIndex, int rows, int columns, int x, int y) {
		for (int height = 0; height < rows; height++) {
			for (int width = 0; width < columns; width++) {
				addSlot(slotCreator.createSlot(startIndex + (width + height * columns), width * 18 + x, height * 18 + y));
			}
		}
	}
}
