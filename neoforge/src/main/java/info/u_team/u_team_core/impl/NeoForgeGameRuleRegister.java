package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.GameRuleRegister;
import info.u_team.u_team_core.impl.common.CommonGameRuleRegister;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class NeoForgeGameRuleRegister extends CommonGameRuleRegister {
	
	NeoForgeGameRuleRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::setup));
	}
	
	private void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> registerEntries());
	}
	
	public static class Factory implements GameRuleRegister.Factory {
		
		@Override
		public GameRuleRegister create() {
			return new NeoForgeGameRuleRegister();
		}
	}
}
