package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.FORGE)
public class TestDimensions {
	
	// TODO wait for forge replacement
	// @SuppressWarnings("deprecation")
	// @SubscribeEvent
	// public static void on(RegisterDimensionsEvent event) {
	// if (!DimensionManager.getRegistry().keySet().contains(TestModDimensions.BASIC.getId())) { // How do we know when the
	// dimension needs to be registered??
	// DimensionManager.registerDimension(TestModDimensions.BASIC.getId(), TestModDimensions.BASIC.get(), null, true);
	// }
	// }
}
