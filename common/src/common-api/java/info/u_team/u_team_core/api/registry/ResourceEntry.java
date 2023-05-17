package info.u_team.u_team_core.api.registry;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;

public interface ResourceEntry<R> extends Supplier<R> {
	
	ResourceLocation getId();
	
	boolean isPresent();
	
}
