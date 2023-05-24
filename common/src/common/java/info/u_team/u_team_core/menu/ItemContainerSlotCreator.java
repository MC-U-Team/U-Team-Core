package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class ItemContainerSlotCreator implements ItemSlotCreator {
	
	public static ItemContainerSlotCreator of(Container container) {
		return new ItemContainerSlotCreator(container);
	}
	
	private final Container container;
	
	protected ItemContainerSlotCreator(Container container) {
		this.container = container;
	}
	
	@Override
	public Slot createSlot(int index, int x, int y) {
		return new Slot(container, index, x, y);
	}
}
