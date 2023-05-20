package info.u_team.u_team_core.api.registry;

import java.util.function.Supplier;

public interface LazyEntry<R> extends Supplier<R> {
	
	boolean isPresent();
}
