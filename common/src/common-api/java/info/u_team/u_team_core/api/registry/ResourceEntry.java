package info.u_team.u_team_core.api.registry;

import net.minecraft.resources.ResourceLocation;

public interface ResourceEntry<R> extends SimpleEntry<R> {
	
	ResourceLocation getId();
}
