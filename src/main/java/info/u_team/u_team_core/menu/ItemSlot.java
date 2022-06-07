package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.inventory.UItemStackHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ItemSlot extends SlotItemHandler {
	
	private final IItemHandler itemHandler;
	private final int slot;
	
	public ItemSlot(IItemHandler itemHandler, int slot, int x, int y) {
		super(itemHandler, slot, x, y);
		this.itemHandler = itemHandler;
		this.slot = slot;
	}
	
	@Override
	public void setChanged() {
		if (itemHandler instanceof final UItemStackHandler uItemHandler) {
			uItemHandler.onContentsChanged(slot);
		}
	}
	
	@Override
	public void initialize(ItemStack stack) {
		set(stack); // TODO is this right to prevent java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
	}
	
}
