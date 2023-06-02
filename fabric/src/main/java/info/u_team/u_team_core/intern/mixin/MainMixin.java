package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.util.ResourceLocationUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.Main;

@Mixin(value = Main.class, priority = 1500)
abstract class MainMixin {
	
	@Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;startTimerHackThread()V", shift = At.Shift.AFTER, ordinal = 0))
	private static void uteamcore$runServer$callRegisterEvents(CallbackInfo info) {
		BuiltInRegistries.REGISTRY.registryKeySet().stream().sorted((first, second) -> {
			return ResourceLocationUtil.nameSpacedComparator().compare(first.location(), second.location());
		}).forEach(SetupEvents.REGISTER.invoker()::onRegister);
	}
}
