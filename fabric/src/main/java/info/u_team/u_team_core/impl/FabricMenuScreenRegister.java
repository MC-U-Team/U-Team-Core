package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.MenuScreenRegister;
import info.u_team.u_team_core.impl.common.CommonMenuScreenRegister;
import info.u_team.u_team_core.util.CastUtil;
import net.minecraft.client.gui.screens.MenuScreens;

public class FabricMenuScreenRegister extends CommonMenuScreenRegister {
	
	FabricMenuScreenRegister() {
	}
	
	@Override
	public void register() {
		screens.forEach((menuTypeSupplier, screenConstructor) -> {
			MenuScreens.register(CastUtil.uncheckedCast(menuTypeSupplier.get()), screenConstructor);
		});
	}
	
	public static class Factory implements MenuScreenRegister.Factory {
		
		@Override
		public MenuScreenRegister create() {
			return new FabricMenuScreenRegister();
		}
	}
	
}
