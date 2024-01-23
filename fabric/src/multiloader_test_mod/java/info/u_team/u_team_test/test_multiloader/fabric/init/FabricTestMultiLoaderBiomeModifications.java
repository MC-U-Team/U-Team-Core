package info.u_team.u_team_test.test_multiloader.fabric.init;

import info.u_team.u_team_core.event.SetupEvents;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEntityTypes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.tags.BiomeTags;

public class FabricTestMultiLoaderBiomeModifications {
	
	private static void setup() {
		BiomeModifications.addSpawn(context -> context.hasTag(BiomeTags.IS_OVERWORLD), TestMultiLoaderEntityTypes.TEST_LIVING.get().getCategory(), TestMultiLoaderEntityTypes.TEST_LIVING.get(), 80, 4, 4);
	}
	
	static void register() {
		SetupEvents.COMMON_SETUP.register(FabricTestMultiLoaderBiomeModifications::setup);
	}
	
}
