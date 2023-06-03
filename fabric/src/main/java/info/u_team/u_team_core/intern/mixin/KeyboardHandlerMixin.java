package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.event.ScreenEvents;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.screens.Screen;

@Mixin(KeyboardHandler.class)
abstract class KeyboardHandlerMixin {
	
	@Inject(method = "method_1454(ILnet/minecraft/client/gui/screens/Screen;[ZIII)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z", shift = Shift.AFTER))
	private static void uteamcore$keyPressPost(int code, Screen screen, boolean[] resultHack, int keyCode, int scanCode, int modifiers, CallbackInfo info) {
		if (!resultHack[0]) {
			resultHack[0] = ScreenEvents.AFTER_KEY_PRESSED.invoker().onKeyPressed(screen, keyCode, scanCode, modifiers);
		}
	}
}
