package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.MenuScreenRegister;
import info.u_team.u_team_core.impl.common.CommonMenuScreenRegister;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class NeoForgeMenuScreenRegister extends CommonMenuScreenRegister {
	
	NeoForgeMenuScreenRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::registerMenuScreens));
	}
	
	private void registerMenuScreens(RegisterMenuScreensEvent event) {
		screens.forEach((supplier, constructor) -> {
			event.register(CastUtil.uncheckedCast(supplier.get()), constructor);
		});
	}
	
	public static class Factory implements MenuScreenRegister.Factory {
		
		@Override
		public MenuScreenRegister create() {
			return new NeoForgeMenuScreenRegister();
		}
	}
	
}
