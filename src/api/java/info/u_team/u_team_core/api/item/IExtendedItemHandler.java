package info.u_team.u_team_core.api.item;

import info.u_team.u_team_core.api.InteractionType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IExtendedItemHandler extends IItemHandlerModifiable {
	
	default ItemStack insertItem(int slot, ItemStack stack, InteractionType action) {
		return insertItem(slot, stack, action.isSimulate());
	}
	
	default ItemStack extractItem(int slot, int amount, InteractionType action) {
		return extractItem(slot, amount, action.isSimulate());
	}
	
}
