package info.u_team.u_team_test.test_multiloader.data.provider.forge;

import java.util.function.Consumer;

import info.u_team.u_team_core.data.CommonDatapackBuiltinEntriesProvider;
import info.u_team.u_team_core.data.GenerationData;
import net.minecraft.data.registries.RegistriesDatapackGenerator;

public class TestMultiLoaderForgeDatapackBuiltinEntriesProvider extends CommonDatapackBuiltinEntriesProvider {
	
	public TestMultiLoaderForgeDatapackBuiltinEntriesProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(Consumer<RegistriesDatapackGenerator> consumer) {
		// consumer.accept(new DatapackBuiltinEntriesProvider(getGenerationData().output(),
		// getGenerationData().lookupProviderFuture(), new RegistrySetBuilder().add(ForgeRegistries.Keys.BIOME_MODIFIERS,
		// context -> {
		// context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(modid(),
		// "add_test_living_entity_to_all_biomes")),
		// AddSpawnsBiomeModifier.singleSpawn(context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD), new
		// SpawnerData(TestMultiLoaderEntityTypes.TEST_LIVING.get(), 80, 4, 4)));
		// }), Set.of(modid())));
	}
	
}
