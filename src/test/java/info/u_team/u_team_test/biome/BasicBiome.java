package info.u_team.u_team_test.biome;

import info.u_team.u_team_test.init.TestBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.*;

public class BasicBiome extends Biome {
	
	public static final SurfaceBuilderConfig BASIC_BLOCK_SURVACE = new SurfaceBuilderConfig(TestBlocks.BASIC.get().getDefaultState(), TestBlocks.BASIC.get().getDefaultState(), TestBlocks.BASIC.get().getDefaultState());
	
	public BasicBiome() {
		super(new Builder().surfaceBuilder(SurfaceBuilder.SHATTERED_SAVANNA, new SurfaceBuilderConfig(TestBlocks.BASIC.get().getDefaultState(), Blocks.END_STONE.getDefaultState(), TestBlocks.BASIC.get().getDefaultState())).precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.125F).scale(0.05F).temperature(1.0F).downfall(1.0F).func_235097_a_(new BiomeAmbience.Builder().func_235246_b_(4159204).func_235248_c_(329011).func_235239_a_(12638463).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).parent((String) null));
		this.func_235063_a_(DefaultBiomeFeatures.field_235183_u_);
		DefaultBiomeFeatures.addMonsterRooms(this);
		DefaultBiomeFeatures.addStoneVariants(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addSedimentDisks(this);
		DefaultBiomeFeatures.addDefaultFlowers(this);
		DefaultBiomeFeatures.addSparseGrass(this);
		DefaultBiomeFeatures.addDeadBushes(this);
		DefaultBiomeFeatures.addMushrooms(this);
		DefaultBiomeFeatures.addExtraReedsPumpkinsCactus(this);
		DefaultBiomeFeatures.addSprings(this);
		DefaultBiomeFeatures.addDesertFeatures(this);
		DefaultBiomeFeatures.addFreezeTopLayer(this);
		this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
		this.addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 19, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.HUSK, 80, 4, 4));
	}
	
}
