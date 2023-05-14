package info.u_team.u_team_core.api.registry;

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface RegistryEntry<R> extends Supplier<R> {
	
	public ResourceLocation getId();
	
	public ResourceKey<R> getKey();
	
	public Optional<Holder<R>> getHolder();
	
	boolean isPresent();
	
}
