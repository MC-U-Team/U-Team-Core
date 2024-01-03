package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;

import info.u_team.u_team_core.api.item.UItemExtension;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemExtension;

@Mixin(Item.class)
abstract class ItemMixin implements IItemExtension {
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		if (this instanceof UItemExtension extension) {
			return extension.shouldPlayUpdateAnimation(oldStack, newStack);
		}
		return IItemExtension.super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
	}
	
	@Override
	public boolean onDroppedByPlayer(ItemStack stack, Player player) {
		if (this instanceof UItemExtension extension) {
			return extension.canBeDropped(stack, player);
		}
		return IItemExtension.super.onDroppedByPlayer(stack, player);
	}
	
}
