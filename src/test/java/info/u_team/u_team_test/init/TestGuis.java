package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.gui.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class TestGuis {
	
	@SubscribeEvent
	public static void register(FMLClientSetupEvent event) {
		ScreenManager.registerFactory(TestContainers.BASIC, BasicTileEntityScreen::new);
		ScreenManager.registerFactory(TestContainers.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
	}
	
}
