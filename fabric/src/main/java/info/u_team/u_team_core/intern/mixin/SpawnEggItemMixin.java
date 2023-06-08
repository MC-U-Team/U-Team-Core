package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import info.u_team.u_team_core.item.UFabricSpawnEggItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;

@Mixin(SpawnEggItem.class)
abstract class SpawnEggItemMixin {
	
	@Inject(method = "byId", at = @At("RETURN"), cancellable = true)
	private static void uteamcore$byId(EntityType<?> type, CallbackInfoReturnable<SpawnEggItem> info) {
		if (info.getReturnValue() == null) {
			info.setReturnValue(UFabricSpawnEggItem.getEgg(type));
		}
	}
	
}
