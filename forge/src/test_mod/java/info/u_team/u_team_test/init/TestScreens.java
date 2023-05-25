package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.client.MenuScreensRegister;
import info.u_team.u_team_test.screen.BasicBlockEntityScreen;
import info.u_team.u_team_test.screen.BasicEnergyCreatorScreen;
import info.u_team.u_team_test.screen.BasicFluidInventoryScreen;
import net.minecraft.Util;

public class TestScreens {
	
	public static final MenuScreensRegister MENU_SCREENS = Util.make(MenuScreensRegister.create(), menuScreens -> {
		menuScreens.register(TestMenuTypes.BASIC_BLOCK_ENTITY, BasicBlockEntityScreen::new);
		menuScreens.register(TestMenuTypes.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
		menuScreens.register(TestMenuTypes.BASIC_FLUID_INVENTORY, BasicFluidInventoryScreen::new);
	});
	
	public static void register() {
		MENU_SCREENS.register();
	}
}
