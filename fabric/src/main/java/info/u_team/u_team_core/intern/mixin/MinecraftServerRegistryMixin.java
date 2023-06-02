package info.u_team.u_team_core.intern.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.api.Platform;
import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.util.ResourceLocationUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;

@Mixin(value = MinecraftServer.class, priority = 500)
abstract class MinecraftServerRegistryMixin {
	
	@Inject(method = "runServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;initServer()Z"))
	private void uteamcore$runServer$callRegisterEvents(CallbackInfo info) {
		if (Platform.getInstance().getEnvironment() == Environment.SERVER) {
			BuiltInRegistries.REGISTRY.registryKeySet().stream().sorted((first, second) -> {
				return ResourceLocationUtil.nameSpacedComparator().compare(first.location(), second.location());
			}).forEach(SetupEvents.REGISTER.invoker()::onRegister);
		}
	}
}
