package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.SpawnPlacementRegister;
import info.u_team.u_team_test.test_multiloader.entity.TestLiving;
import net.minecraft.Util;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class TestMultiLoaderSpawnPlacements {
	
	public static final SpawnPlacementRegister SPAWN_PLACEMENTS = Util.make(SpawnPlacementRegister.create(), spawnPlacements -> {
		spawnPlacements.register(TestMultiLoaderEntityTypes.TEST_LIVING, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TestLiving::checkTestLivingSpawnRules);
	});
	
	static void register() {
		SPAWN_PLACEMENTS.register();
	}
	
}
