package info.u_team.u_team_core.impl.common;

import java.util.HashSet;
import java.util.Set;

import info.u_team.u_team_core.api.registry.ColorProviderRegister;

public abstract class CommonColorProviderRegister implements ColorProviderRegister {
	
	protected final Set<BlockRegisterHandler> blockEntries;
	protected final Set<ItemRegisterHandler> itemEntries;
	
	protected CommonColorProviderRegister() {
		blockEntries = new HashSet<>();
		itemEntries = new HashSet<>();
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
