package info.u_team.u_team_test.init;

import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.*;

public class TestBiomeLoadingAdditions {
	
	private static void biomeLoadingAddition(BiomeLoadingEvent event) {
		if (event.getCategory() != Category.THEEND && event.getCategory() != Category.NETHER) {
			event.getSpawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(TestEntityTypes.TEST_LIVING.get(), 80, 4, 4));
		}
	}
	
	public static void registerForge(IEventBus bus) {
		bus.addListener(EventPriority.HIGH, TestBiomeLoadingAdditions::biomeLoadingAddition);
	}
	
}
