package info.u_team.u_team_core.container;

import info.u_team.u_team_core.inventory.UItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ItemSlot extends SlotItemHandler {
	
	private final IItemHandler itemHandler;
	private final int index;
	
	public ItemSlot(IItemHandler itemHandler, int index, int x, int y) {
		super(itemHandler, index, x, y);
		this.itemHandler = itemHandler;
		this.index = index;
	}
	
	@Override
	public void setChanged() {
		if (itemHandler instanceof UItemStackHandler) {
			((UItemStackHandler) itemHandler).onContentsChanged(index);
		}
	}
	
}
