package info.u_team.u_team_core.impl.common;

import java.util.LinkedHashSet;
import java.util.Set;

import info.u_team.u_team_core.api.registry.ColorProviderRegister;

public abstract class CommonColorProviderRegister implements ColorProviderRegister {
	
	protected final Set<BlockRegisterHandler> blockEntries;
	protected final Set<ItemRegisterHandler> itemEntries;
	
	protected CommonColorProviderRegister() {
		blockEntries = new LinkedHashSet<>();
		itemEntries = new LinkedHashSet<>();
	}
	
	@Override
	public void register(BlockRegisterHandler handler) {
		blockEntries.add(handler);
	}
	
	@Override
	public void register(ItemRegisterHandler handler) {
		itemEntries.add(handler);
	}
	
}
