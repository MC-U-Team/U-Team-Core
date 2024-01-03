package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.items.IItemHandler;

public class ItemHandlerSlotCreator implements ItemSlotCreator {
	
	public static ItemHandlerSlotCreator of(IItemHandler handler) {
		return new ItemHandlerSlotCreator(handler);
	}
	
	private final IItemHandler handler;
	
	protected ItemHandlerSlotCreator(IItemHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public Slot createSlot(int index, int x, int y) {
		return new ItemSlot(handler, index, x, y);
	}
}
