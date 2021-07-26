package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.ClientRegistry;
import info.u_team.u_team_test.screen.BasicEnergyCreatorScreen;
import info.u_team.u_team_test.screen.BasicFluidInventoryScreen;
import info.u_team.u_team_test.screen.BasicTileEntityScreen;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class TestScreens {

	private static void setup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegistry.registerScreen(TestContainers.BASIC, BasicTileEntityScreen::new);
			ClientRegistry.registerScreen(TestContainers.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
			ClientRegistry.registerScreen(TestContainers.BASIC_FLUID_INVENTORY, BasicFluidInventoryScreen::new);
		});
	}

	public static void registerMod(IEventBus bus) {
		bus.addListener(TestScreens::setup);
	}
}
