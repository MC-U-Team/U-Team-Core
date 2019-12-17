package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.FORGE)
public class TestDimensions {
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void on(RegisterDimensionsEvent event) {
		if (!DimensionManager.getRegistry().keySet().contains(TestModDimensions.BASIC.getRegistryName())) { // How do we know when the dimension needs to be registered??
			DimensionManager.registerDimension(TestModDimensions.BASIC.getRegistryName(), TestModDimensions.BASIC, null, true);
		}
	}
}
