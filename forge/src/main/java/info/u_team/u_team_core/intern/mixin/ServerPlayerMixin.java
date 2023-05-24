package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.menu.UAbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

@Mixin(ServerPlayer.class)
abstract class ServerPlayerMixin {
	
	@Inject(method = "initMenu", at = @At("HEAD"))
	private void uteamcore$initMenu(AbstractContainerMenu menu, CallbackInfo callbackInfo) {
		if (menu instanceof final UAbstractContainerMenu uContainerMenu) {
			final ServerPlayer player = (ServerPlayer) (Object) this;
			uContainerMenu.setSynchronizerPlayer(player);
			uContainerMenu.initMenu(player);
		}
	}
}
