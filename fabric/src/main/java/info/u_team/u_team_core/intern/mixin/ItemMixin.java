package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;

import info.u_team.u_team_core.api.item.UItemExtension;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin(Item.class)
abstract class ItemMixin implements FabricItem {
	
	@Override
	public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
		if (this instanceof UItemExtension extension) {
			return extension.shouldPlayUpdateAnimation(oldStack, newStack);
		}
		return FabricItem.super.allowNbtUpdateAnimation(player, hand, oldStack, newStack);
	}
}
