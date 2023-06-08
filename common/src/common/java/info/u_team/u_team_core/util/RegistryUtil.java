package info.u_team.u_team_core.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class RegistryUtil {
	
	public static <T> Registry<T> getBuiltInRegistry(ResourceKey<? extends Registry<T>> key) {
		return CastUtil.uncheckedCast(BuiltInRegistries.REGISTRY.get(key.location()));
	}
	
	public static <T> Registry<T> getRegistry(Level level, ResourceKey<? extends Registry<T>> key) {
		return level.registryAccess().registryOrThrow(key);
	}
	
}
