package info.u_team.u_team_test.init;

import info.u_team.u_team_test.entity.TestLivingEntity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class TestEntitySpawnPlacementRegistries {
	
	private static void setup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EntitySpawnPlacementRegistry.register(TestEntityTypes.TEST_LIVING.get(), PlacementType.ON_GROUND, Type.MOTION_BLOCKING_NO_LEAVES, TestLivingEntity::canSpawn);
		});
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestEntitySpawnPlacementRegistries::setup);
	}
	
}
