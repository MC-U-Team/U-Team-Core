package info.u_team.u_team_test.test_multiloader.data.provider.neoforge;

import java.util.Set;
import java.util.function.Consumer;

import info.u_team.u_team_core.data.CommonDatapackBuiltinEntriesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEntityTypes;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class TestMultiLoaderNeoForgeDatapackBuiltinEntriesProvider extends CommonDatapackBuiltinEntriesProvider {
	
	public TestMultiLoaderNeoForgeDatapackBuiltinEntriesProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(Consumer<DatapackBuiltinEntriesProvider> consumer) {
		consumer.accept(new DatapackBuiltinEntriesProvider(getGenerationData().output(), getGenerationData().lookupProviderFuture(), new RegistrySetBuilder().add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
			context.register(ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(modid(), "add_test_living_entity_to_all_biomes")), AddSpawnsBiomeModifier.singleSpawn(context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD), new SpawnerData(TestMultiLoaderEntityTypes.TEST_LIVING.get(), 80, 4, 4)));
		}), Set.of(modid())));
	}
	
}
