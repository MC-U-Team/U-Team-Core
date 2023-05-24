package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class ContainerItemSlotCreator implements ItemSlotCreator {
	
	public static ContainerItemSlotCreator of(Container container) {
		return new ContainerItemSlotCreator(container);
	}
	
	private final Container container;
	
	protected ContainerItemSlotCreator(Container container) {
		this.container = container;
	}
	
	@Override
	public Slot createSlot(int index, int x, int y) {
		return new Slot(container, index, x, y);
	}
}
