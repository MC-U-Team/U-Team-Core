package info.u_team.u_team_test.biome;

import info.u_team.u_team_core.biome.UBiome;
import info.u_team.u_team_test.init.TestBlocks;
import net.minecraft.entity.*;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.GenerationStage.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.surfacebuilders.*;

public class BiomeBasic extends UBiome {
	
	public static final SurfaceBuilderConfig BASIC_BLOCK_SURVACE = new SurfaceBuilderConfig(TestBlocks.basic.getDefaultState(), TestBlocks.basic.getDefaultState(), TestBlocks.basic.getDefaultState());
	
	public BiomeBasic(String name) {
		super(name, new BiomeBuilder().surfaceBuilder(new CompositeSurfaceBuilder<>(DEFAULT_SURFACE_BUILDER, BASIC_BLOCK_SURVACE)).precipitation(RainType.NONE).category(Category.DESERT).depth(0.1F).scale(0.2F).temperature(0.95F).downfall(0.8F).waterColor(4159204).waterFogColor(329011));
		addStructure(Feature.MINESHAFT, new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL));
		addStructure(Feature.STRONGHOLD, new StrongholdConfig());
		addStructure(Feature.STRONGHOLD, new StrongholdConfig());
		addCarver(Carving.AIR, createWorldCarverWrapper(CAVE_WORLD_CARVER, new ProbabilityConfig(0.14285715F)));
		addCarver(Carving.AIR, createWorldCarverWrapper(CANYON_WORLD_CARVER, new ProbabilityConfig(0.02F)));
		addStructureFeatures();
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIRT.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 256)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRAVEL.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(8, 0, 0, 256)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GRANITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 80)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIORITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 80)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.ANDESITE.getDefaultState(), 33), COUNT_RANGE, new CountRangeConfig(10, 0, 0, 80)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.COAL_ORE.getDefaultState(), 17), COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.IRON_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(20, 0, 0, 64)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.GOLD_ORE.getDefaultState(), 9), COUNT_RANGE, new CountRangeConfig(2, 0, 0, 32)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.REDSTONE_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(8, 0, 0, 16)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.DIAMOND_ORE.getDefaultState(), 8), COUNT_RANGE, new CountRangeConfig(1, 0, 0, 16)));
		addFeature(Decoration.UNDERGROUND_ORES, createCompositeFeature(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, Blocks.LAPIS_ORE.getDefaultState(), 7), DEPTH_AVERAGE, new DepthAverageConfig(1, 16, 16)));
		addSpawn(EnumCreatureType.CREATURE, new SpawnListEntry(EntityType.VILLAGER, 12, 4, 4));
	}
	
	// public void registerToForge() {
	// BiomeManager.addBiome(BiomeType.DESERT, new BiomeEntry(this, 10000));
	// BiomeDictionary.addTypes(this, BiomeDictionary.Type.HOT,
	// BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY);
	// }
	
}
