package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.util.ResourceLocationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;

@Mixin(value = Minecraft.class, priority = 500)
abstract class MinecraftRegistryMixin {
	
	@Inject(method = "run", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameThread:Ljava/lang/Thread;", shift = At.Shift.AFTER, ordinal = 0))
	private void uteamcore$run$callRegisterEvents(CallbackInfo info) {
		BuiltInRegistries.REGISTRY.registryKeySet().stream().sorted((first, second) -> {
			return ResourceLocationUtil.nameSpacedComparator().compare(first.location(), second.location());
		}).forEach(SetupEvents.REGISTER.invoker()::onRegister);
	}
}
