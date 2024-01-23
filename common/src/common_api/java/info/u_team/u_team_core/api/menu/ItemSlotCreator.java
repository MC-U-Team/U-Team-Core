package info.u_team.u_team_core.api.menu;

import net.minecraft.world.inventory.Slot;

public interface ItemSlotCreator {
	
	Slot createSlot(int index, int x, int y);
}
