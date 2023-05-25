package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.MenuScreensRegister;
import info.u_team.u_team_core.impl.common.CommonMenuScreensRegister;
import info.u_team.u_team_core.util.CastUtil;
import net.minecraft.client.gui.screens.MenuScreens;

public class FabricMenuScreensRegister extends CommonMenuScreensRegister {
	
	FabricMenuScreensRegister() {
	}
	
	@Override
	public void register() {
		screens.forEach((menuTypeSupplier, screenConstructor) -> {
			MenuScreens.register(CastUtil.uncheckedCast(menuTypeSupplier.get()), screenConstructor);
		});
	}
	
	public static class Factory implements MenuScreensRegister.Factory {
		
		@Override
		public MenuScreensRegister create() {
			return new FabricMenuScreensRegister();
		}
	}
	
}
