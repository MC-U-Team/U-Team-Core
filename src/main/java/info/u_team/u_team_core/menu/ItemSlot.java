package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.inventory.UItemStackHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class ItemSlot extends SlotItemHandler {
	
	private final IItemHandlerModifiable itemHandler;
	private final int slot;
	
	public ItemSlot(IItemHandlerModifiable itemHandler, int slot, int x, int y) {
		super(itemHandler, slot, x, y);
		this.itemHandler = itemHandler;
		this.slot = slot;
	}
	
	@Override
	public void setChanged() {
		if (itemHandler instanceof UItemStackHandler uItemHandler) {
			uItemHandler.onContentsChanged(slot);
		}
	}
	
}
