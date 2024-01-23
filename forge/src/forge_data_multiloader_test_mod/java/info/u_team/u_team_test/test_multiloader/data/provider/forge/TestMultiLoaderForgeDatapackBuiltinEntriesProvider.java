package info.u_team.u_team_test.test_multiloader.data.provider.forge;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

import info.u_team.u_team_core.data.CommonDatapackBuiltinEntriesProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderEntityTypes;
import net.minecraft.core.Cloner;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.registries.DataPackRegistriesHooks;
import net.minecraftforge.registries.ForgeRegistries;

public class TestMultiLoaderForgeDatapackBuiltinEntriesProvider extends CommonDatapackBuiltinEntriesProvider {
	
	public TestMultiLoaderForgeDatapackBuiltinEntriesProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(Consumer<RegistriesDatapackGenerator> consumer) {
		// TODO wait for https://github.com/MinecraftForge/MinecraftForge/pull/9848 to use DatapackBuiltinEntriesProvider again
		consumer.accept(new RegistriesDatapackGenerator(getGenerationData().output(), createLookup(getGenerationData().lookupProviderFuture(), new RegistrySetBuilder() //
				.add(ForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
					context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(modid(), "add_test_living_entity_to_all_biomes")), AddSpawnsBiomeModifier.singleSpawn(context.lookup(ForgeRegistries.Keys.BIOMES).getOrThrow(BiomeTags.IS_OVERWORLD), new SpawnerData(TestMultiLoaderEntityTypes.TEST_LIVING.get(), 80, 4, 4)));
				})).thenApply(RegistrySetBuilder.PatchedRegistries::patches), Set.of(modid())));
		
		// consumer.accept(new DatapackBuiltinEntriesProvider(getGenerationData().output(),
		// getGenerationData().lookupProviderFuture(), new RegistrySetBuilder().add(ForgeRegistries.Keys.BIOME_MODIFIERS,
		// context -> {
		// context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(modid(),
		// "add_test_living_entity_to_all_biomes")),
		// AddSpawnsBiomeModifier.singleSpawn(context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD), new
		// SpawnerData(TestMultiLoaderEntityTypes.TEST_LIVING.get(), 80, 4, 4)));
		// }), Set.of(modid())));
	}
	
	// Copied from RegistryLookup.createLookup and adapted
	@Deprecated
	private static CompletableFuture<RegistrySetBuilder.PatchedRegistries> createLookup(CompletableFuture<HolderLookup.Provider> pLookup, RegistrySetBuilder pRegistrySetBuilder) {
		return pLookup.thenApply((provider) -> {
			RegistryAccess.Frozen registryaccess$frozen = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
			Cloner.Factory cloner$factory = new Cloner.Factory();
			HolderLookup.Provider merged = new HolderLookup.Provider() {
				
				private final HolderLookup.Provider forgeRegistryProvider = new RegistrySetBuilder().add(ForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
				}).build(registryaccess$frozen);
				
				@Override
				public <T> Optional<RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> pRegistryKey) {
					return forgeRegistryProvider.lookup(pRegistryKey).or(() -> provider.lookup(pRegistryKey));
				}
				
				@Override
				public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
					return Stream.concat(Stream.of(ForgeRegistries.Keys.BIOME_MODIFIERS), provider.listRegistries());
				}
			};
			DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().forEach(registryData -> registryData.runWithArguments(cloner$factory::addCodec));
			RegistrySetBuilder.PatchedRegistries registrysetbuilder$patchedregistries = pRegistrySetBuilder.buildPatch(registryaccess$frozen, merged, cloner$factory);
			HolderLookup.Provider holderlookup$provider = registrysetbuilder$patchedregistries.full();
			Optional<HolderLookup.RegistryLookup<Biome>> optional = holderlookup$provider.lookup(Registries.BIOME);
			Optional<HolderLookup.RegistryLookup<PlacedFeature>> optional1 = holderlookup$provider.lookup(Registries.PLACED_FEATURE);
			if (optional.isPresent() || optional1.isPresent()) {
				VanillaRegistries.validateThatAllBiomeFeaturesHaveBiomeFilter(optional1.orElseGet(() -> {
					return merged.lookupOrThrow(Registries.PLACED_FEATURE);
				}), optional.orElseGet(() -> {
					return merged.lookupOrThrow(Registries.BIOME);
				}));
			}
			return registrysetbuilder$patchedregistries;
		});
	}
	
}
