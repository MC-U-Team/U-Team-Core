package info.u_team.u_team_core.api.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface UItemExtension {
	
	/**
	 * Should play update animations when e.g. switching item slots or updating stacks
	 * @param oldStack Old stack
	 * @param newStack New stack
	 * @return Return true to update
	 */
	default boolean shouldPlayUpdateAnimation(ItemStack oldStack, ItemStack newStack) {
		return !oldStack.equals(newStack);
	}
	
	/**
	 * Should the player be able to drop this item?
	 * @param stack Stack
	 * @param player Player
	 * @return Return true to allow drops
	 */
	default boolean canBeDropped(ItemStack stack, Player player) {
		return true;
	}
	
}
