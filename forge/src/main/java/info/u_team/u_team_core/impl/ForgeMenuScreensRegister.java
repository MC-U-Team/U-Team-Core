package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.MenuScreensRegister;
import info.u_team.u_team_core.impl.common.CommonMenuScreensRegister;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ForgeMenuScreensRegister extends CommonMenuScreensRegister {
	
	ForgeMenuScreensRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::setup));
	}
	
	private void setup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> screens.forEach((menuTypeSupplier, screenConstructor) -> {
			MenuScreens.register(CastUtil.uncheckedCast(menuTypeSupplier.get()), screenConstructor);
		}));
	}
	
	public static class Factory implements MenuScreensRegister.Factory {
		
		@Override
		public MenuScreensRegister create() {
			return new ForgeMenuScreensRegister();
		}
	}
	
}
