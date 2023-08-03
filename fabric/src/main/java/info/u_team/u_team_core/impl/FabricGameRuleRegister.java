package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.GameRuleRegister;
import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_core.impl.common.CommonGameRuleRegister;

public class FabricGameRuleRegister extends CommonGameRuleRegister {
	
	FabricGameRuleRegister() {
	}
	
	@Override
	public void register() {
		SetupEvents.COMMON_SETUP.register(this::registerEntries);
	}
	
	public static class Factory implements GameRuleRegister.Factory {
		
		@Override
		public GameRuleRegister create() {
			return new FabricGameRuleRegister();
		}
	}
}
