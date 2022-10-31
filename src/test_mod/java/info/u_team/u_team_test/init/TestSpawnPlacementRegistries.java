package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.TestLiving;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestSpawnPlacementRegistries {
	
	private static void register(SpawnPlacementRegisterEvent event) {
		event.register(TestEntityTypes.TEST_LIVING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TestLiving::checkTestLivingSpawnRules, Operation.OR);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestSpawnPlacementRegistries::register);
	}
	
}
