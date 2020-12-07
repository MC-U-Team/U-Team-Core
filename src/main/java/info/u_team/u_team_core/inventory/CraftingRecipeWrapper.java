package info.u_team.u_team_core.inventory;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraftforge.items.IItemHandlerModifiable;

public class CraftingRecipeWrapper extends CraftingInventory {
	
	protected final IItemHandlerModifiable inventory;
	
	public CraftingRecipeWrapper(IItemHandlerModifiable inventory, int width, int height) {
		super(null, width, height);
		this.inventory = inventory;
	}
	
	@Override
	public int getSizeInventory() {
		return inventory.getSlots();
	}
	
	@Override
	public boolean isEmpty() {
		for (int index = 0; index < inventory.getSlots(); index++) {
			if (!inventory.getStackInSlot(index).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.getStackInSlot(slot);
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		final ItemStack stack = getStackInSlot(index);
		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		setInventorySlotContents(index, ItemStack.EMPTY);
		return stack;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int count) {
		final ItemStack stack = inventory.getStackInSlot(slot);
		return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory.setStackInSlot(slot, stack);
	}
	
	@Override
	public void clear() {
		for (int index = 0; index < inventory.getSlots(); index++) {
			inventory.setStackInSlot(index, ItemStack.EMPTY);
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return inventory.isItemValid(slot, stack);
	}
	
	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for (int index = 0; index < inventory.getSlots(); index++) {
			final ItemStack stack = inventory.getStackInSlot(index);
			helper.accountPlainStack(stack);
		}
	}
}
