package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.api.item.UItemExtension;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemEntity.class)
abstract class ItemEntityMixin {
	
	@Inject(method = "tick()V", at = @At(value = "HEAD"), cancellable = true)
	private void uteamcore$tick(CallbackInfo info) {
		final ItemStack stack = getItem();
		if (stack.getItem() instanceof final UItemExtension extension) {
			if (extension.updateItemEntity(stack, ((ItemEntity) (Object) this))) {
				info.cancel();
			}
		}
	}
	
	@Shadow
	protected abstract ItemStack getItem();
	
}
