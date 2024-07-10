package info.u_team.u_team_core.intern.mixin;

import java.util.LinkedHashSet;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import info.u_team.u_team_core.event.SetupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

@Mixin(value = BuiltInRegistries.class, priority = 1500)
abstract class BuiltInRegistriesMixin {
	
	@Inject(method = "bootStrap", at = @At(value = "HEAD"))
	private static void uteamcore$bootStrap$callRegisterEvents(CallbackInfo info) {
		final Set<ResourceKey<? extends Registry<?>>> ordered = new LinkedHashSet<>();
		ordered.add(Registries.ATTRIBUTE); // Fix order
		ordered.add(Registries.DATA_COMPONENT_TYPE); // Fix order
		ordered.add(Registries.ARMOR_MATERIAL); // Fix order
		BuiltInRegistries.REGISTRY.stream().map(Registry::key).forEach(ordered::add);
		
		ordered.forEach(SetupEvents.REGISTER.invoker()::onRegister);
	}
	
}
