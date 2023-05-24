package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerItemSlotCreator implements ItemSlotCreator {
	
	public static ItemHandlerItemSlotCreator of(IItemHandler handler) {
		return new ItemHandlerItemSlotCreator(handler);
	}
	
	private final IItemHandler handler;
	
	protected ItemHandlerItemSlotCreator(IItemHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public Slot createSlot(int index, int x, int y) {
		return new ItemSlot(handler, index, x, y);
	}
}
