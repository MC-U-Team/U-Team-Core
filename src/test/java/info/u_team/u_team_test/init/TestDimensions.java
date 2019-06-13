package info.u_team.u_team_test.init;

import info.u_team.u_team_core.dimension.UModDimension;
import info.u_team.u_team_core.registry.DimensionRegistry;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.dimension.DimensionBasic;
import net.minecraftforge.common.*;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestMod.modid, bus = Bus.FORGE)
public class TestDimensions {
	
	public static final ModDimension basic = new UModDimension("basic", DimensionBasic::new);
	
	public static void construct() {
		DimensionRegistry.register(TestMod.modid, TestDimensions.class);
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void on(RegisterDimensionsEvent event) {
		if (!DimensionManager.getRegistry().containsKey(basic.getRegistryName())) { // How do we know when the dimension needs to be registered??
			DimensionManager.registerDimension(basic.getRegistryName(), basic, null, true);
		}
	}
}
