package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.TestLivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class TestEntitySpawnPlacementRegistries {
	
	private static void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			SpawnPlacements.register(TestEntityTypes.TEST_LIVING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TestLivingEntity::canSpawn);
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestEntitySpawnPlacementRegistries::setup);
	}
	
}
