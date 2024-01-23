package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.client.MenuScreenRegister;
import info.u_team.u_team_test.test_multiloader.screen.TestInventoryScreen;
import net.minecraft.Util;

public class TestMultiLoaderScreens {
	
	private static final MenuScreenRegister MENU_SCREENS = Util.make(MenuScreenRegister.create(), menuScreens -> {
		menuScreens.register(TestMultiLoaderMenuTypes.TEST_INVENTORY, TestInventoryScreen::new);
	});
	
	static void register() {
		MENU_SCREENS.register();
	}
}
