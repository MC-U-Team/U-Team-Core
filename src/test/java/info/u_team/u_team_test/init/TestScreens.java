package info.u_team.u_team_test.init;

import info.u_team.u_team_core.util.registry.*;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.screen.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class TestScreens {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		MainThreadWorker.run(() -> {
			ClientRegistry.registerScreen(TestContainers.BASIC, BasicTileEntityScreen::new);
			ClientRegistry.registerScreen(TestContainers.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
			ClientRegistry.registerScreen(TestContainers.BASIC_FLUID_INVENTORY, BasicFluidInventoryScreen::new);
		});
	}
}
