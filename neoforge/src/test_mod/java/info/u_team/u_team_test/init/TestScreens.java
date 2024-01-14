package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.client.MenuScreenRegister;
import info.u_team.u_team_test.screen.BasicEnergyCreatorScreen;
import info.u_team.u_team_test.screen.BasicFluidInventoryScreen;
import net.minecraft.Util;

public class TestScreens {
	
	public static final MenuScreenRegister MENU_SCREENS = Util.make(MenuScreenRegister.create(), menuScreens -> {
		menuScreens.register(TestMenuTypes.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
		menuScreens.register(TestMenuTypes.BASIC_FLUID_INVENTORY, BasicFluidInventoryScreen::new);
	});
	
	static void register() {
		MENU_SCREENS.register();
	}
}
