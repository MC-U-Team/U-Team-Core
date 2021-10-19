package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.TestLiving;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class TestSpawnPlacementRegistries {
	
	private static void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			SpawnPlacements.register(TestEntityTypes.TEST_LIVING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TestLiving::checkTestLivingSpawnRules);
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestSpawnPlacementRegistries::setup);
	}
	
}
