package info.u_team.u_team_test.init;

import info.u_team.u_team_test.TestMod;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

@EventBusSubscriber(modid = TestMod.MODID, bus = Bus.FORGE)
public class TestDimensions {
	
	@SubscribeEvent
	public static void on(RegisterDimensionsEvent event) {
		// Dimension are broken currently
		// if (!DimensionManager.getRegistry().containsKey(TestModDimensions.BASIC.getRegistryName())) { // How do we know when
		// the dimension needs to be registered??
		// DimensionManager.registerDimension(TestModDimensions.BASIC.getRegistryName(), TestModDimensions.BASIC, null, true);
		// }
	}
	
	@SubscribeEvent
	public static void on(EntityTravelToDimensionEvent event) {
		System.out.println("START TRAVELING TO " + event.getDimension() + " AS " + event.getEntity());
	}
	
	@SubscribeEvent
	public static void on(PlayerChangedDimensionEvent event) {
		System.out.println("FINISHED TRAVELING TO: " + event.getTo() + " FROM " + event.getFrom() + " AS " + event.getPlayer());
	}
	
}
