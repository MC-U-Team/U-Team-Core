package info.u_team.u_team_core.impl;

import info.u_team.u_team_core.api.registry.client.MenuScreenRegister;
import info.u_team.u_team_core.impl.common.CommonMenuScreenRegister;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.registry.BusRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ForgeMenuScreenRegister extends CommonMenuScreenRegister {
	
	ForgeMenuScreenRegister() {
	}
	
	@Override
	public void register() {
		BusRegister.registerMod(bus -> bus.addListener(this::setup));
	}
	
	private void setup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> screens.forEach((supplier, constructor) -> {
			MenuScreens.register(CastUtil.uncheckedCast(supplier.get()), constructor);
		}));
	}
	
	public static class Factory implements MenuScreenRegister.Factory {
		
		@Override
		public MenuScreenRegister create() {
			return new ForgeMenuScreenRegister();
		}
	}
	
}
