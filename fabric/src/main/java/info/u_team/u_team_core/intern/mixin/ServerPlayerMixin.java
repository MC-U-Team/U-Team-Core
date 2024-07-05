package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;

import info.u_team.u_team_core.api.item.UItemExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerMixin extends Player {
	
	private ServerPlayerMixin(Level level, BlockPos pos, float yaw, GameProfile profile) {
		super(level, pos, yaw, profile);
	}
	
	@Inject(method = "drop(Z)Z", at = @At(value = "HEAD"), cancellable = true)
	private void uteamcore$drop(boolean dropStack, CallbackInfoReturnable<Boolean> info) {
		final ItemStack selected = getInventory().getSelected();
		if (selected.isEmpty() || (selected.getItem() instanceof final UItemExtension extension && !extension.canBeDropped(selected, this))) {
			info.setReturnValue(false);
		}
	}
	
}
