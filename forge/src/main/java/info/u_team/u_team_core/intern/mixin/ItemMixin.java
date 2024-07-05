package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;

import info.u_team.u_team_core.api.item.UItemExtension;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;

@Mixin(Item.class)
abstract class ItemMixin implements IForgeItem {
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		if (this instanceof final UItemExtension extension) {
			return extension.shouldPlayUpdateAnimation(oldStack, newStack);
		}
		return IForgeItem.super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack stack, Player player) {
		if (this instanceof final UItemExtension extension) {
			return extension.canBeDropped(stack, player);
		}
		return IForgeItem.super.onDroppedByPlayer(stack, player);
	}
	
}
