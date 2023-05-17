package info.u_team.u_team_core.api.registry;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

public interface RegistryEntry<R> extends ResourceEntry<R> {
	
	ResourceKey<R> getKey();
	
	Optional<Holder<R>> getHolder();
	
}
