package info.u_team.u_team_core.inventory;

import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class CraftingRecipeWrapper extends TransientCraftingContainer {
	
	protected final IItemHandlerModifiable inventory;
	
	public CraftingRecipeWrapper(IItemHandlerModifiable inventory, int width, int height) {
		super(null, width, height);
		this.inventory = inventory;
	}
	
	@Override
	public int getContainerSize() {
		return inventory.getSlots();
	}
	
	@Override
	public boolean isEmpty() {
		for (int slot = 0; slot < inventory.getSlots(); slot++) {
			if (!inventory.getStackInSlot(slot).isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public ItemStack getItem(int slot) {
		return inventory.getStackInSlot(slot);
	}
	
	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		final ItemStack stack = getItem(slot);
		if (stack.isEmpty()) {
			return ItemStack.EMPTY;
		}
		setItem(slot, ItemStack.EMPTY);
		return stack;
	}
	
	@Override
	public ItemStack removeItem(int slot, int count) {
		final ItemStack stack = inventory.getStackInSlot(slot);
		return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
	}
	
	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory.setStackInSlot(slot, stack);
	}
	
	@Override
	public void clearContent() {
		for (int slot = 0; slot < inventory.getSlots(); slot++) {
			inventory.setStackInSlot(slot, ItemStack.EMPTY);
		}
	}
	
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		return inventory.isItemValid(slot, stack);
	}
	
	@Override
	public void fillStackedContents(StackedContents helper) {
		for (int slot = 0; slot < inventory.getSlots(); slot++) {
			helper.accountSimpleStack(inventory.getStackInSlot(slot));
		}
	}
}
