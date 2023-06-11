package info.u_team.u_team_core.api.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface UItemExtension {
	
	default boolean shouldPlayUpdateAnimation(ItemStack oldStack, ItemStack newStack) {
		return !oldStack.equals(newStack);
	}
	
	default boolean canBeDropped(ItemStack stack, Player player) {
		return true;
	}
	
}
