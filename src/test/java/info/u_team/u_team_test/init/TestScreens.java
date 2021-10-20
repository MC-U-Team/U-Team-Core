package info.u_team.u_team_test.init;

import info.u_team.u_team_core.event.RegisterMenuScreensEvent;
import info.u_team.u_team_test.screen.BasicEnergyCreatorScreen;
import info.u_team.u_team_test.screen.BasicFluidInventoryScreen;
import info.u_team.u_team_test.screen.BasicBlockEntityScreen;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestScreens {
	
	private static void register(RegisterMenuScreensEvent event) {
		event.registerScreen(TestMenuTypes.BASIC_BLOCK_ENTITY, BasicBlockEntityScreen::new);
		event.registerScreen(TestMenuTypes.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
		event.registerScreen(TestMenuTypes.BASIC_FLUID_INVENTORY, BasicFluidInventoryScreen::new);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestScreens::register);
	}
}
