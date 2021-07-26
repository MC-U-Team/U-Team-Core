package info.u_team.u_team_test.init;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestBiomeLoadingAdditions {

	private static void biomeLoadingAddition(BiomeLoadingEvent event) {
		if (event.getCategory() != BiomeCategory.THEEND && event.getCategory() != BiomeCategory.NETHER) {
			event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TestEntityTypes.TEST_LIVING.get(), 80, 4, 4));
		}
	}

	public static void registerForge(IEventBus bus) {
		bus.addListener(EventPriority.HIGH, TestBiomeLoadingAdditions::biomeLoadingAddition);
	}

}
