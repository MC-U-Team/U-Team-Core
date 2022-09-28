package info.u_team.u_team_test.data.provider;

import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import info.u_team.u_team_core.data.CommonJsonCodecsProvider;
import info.u_team.u_team_core.data.GenerationData;
import info.u_team.u_team_test.init.TestEntityTypes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class TestJsonCodecsProvider extends CommonJsonCodecsProvider {
	
	public TestJsonCodecsProvider(GenerationData generationData) {
		super(generationData);
	}
	
	@Override
	public void register(Consumer<JsonCodecProvider<?>> data) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		
		data.accept(dataPackRegistry(ops, ForgeRegistries.Keys.BIOME_MODIFIERS, Map.of(new ResourceLocation(modid(), "add_test_living_entity_to_all_biomes"), AddSpawnsBiomeModifier.singleSpawn(new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD), new SpawnerData(TestEntityTypes.TEST_LIVING.get(), 80, 4, 4)))));
	}
	
}
