package info.u_team.u_team_core.impl.common;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

import info.u_team.u_team_core.api.registry.CommandRegister;

public abstract class CommonCommandRegister implements CommandRegister {
	
	protected final Set<Consumer<CommandHandler>> entries;
	
	protected CommonCommandRegister() {
		entries = new LinkedHashSet<>();
	}
	
	@Override
	public void register(Consumer<CommandHandler> consumer) {
		entries.add(consumer);
	}
}
