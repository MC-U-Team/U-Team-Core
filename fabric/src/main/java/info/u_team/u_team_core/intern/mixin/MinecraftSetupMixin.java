package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.event.SetupEvents;
import net.minecraft.client.Minecraft;

@Mixin(value = Minecraft.class, priority = 1500)
abstract class MinecraftSetupMixin {
	
	@Inject(method = "run", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameThread:Ljava/lang/Thread;", shift = At.Shift.AFTER, ordinal = 0))
	private void uteamcore$run$callCommonSetupEvent(CallbackInfo info) {
		SetupEvents.COMMON_SETUP.invoker().onSetup();
	}
}
