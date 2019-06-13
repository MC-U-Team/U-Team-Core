package info.u_team.u_team_test.biome;

import info.u_team.u_team_core.biome.UBiome;
import info.u_team.u_team_test.init.TestBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.surfacebuilders.*;

public class BiomeBasic extends UBiome {
	
	public static final SurfaceBuilderConfig BASIC_BLOCK_SURVACE = new SurfaceBuilderConfig(TestBlocks.basic.getDefaultState(), TestBlocks.basic.getDefaultState(), TestBlocks.basic.getDefaultState());
	
	public BiomeBasic(String name) {
		super(name, (new Builder()).surfaceBuilder(SurfaceBuilder.SHATTERED_SAVANNA, new SurfaceBuilderConfig(TestBlocks.basic.getDefaultState(), Blocks.END_STONE.getDefaultState(), TestBlocks.basic.getDefaultState())).precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.125F).scale(0.05F).temperature(1.0F).downfall(1.0F).waterColor(4159204).waterFogColor(329011).parent((String) null));
		this.addStructure(Feature.VILLAGE, new VillageConfig("village/desert/town_centers", 6));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addStructures(this);
		DefaultBiomeFeatures.addMonsterRooms(this);
		DefaultBiomeFeatures.addStoneVariants(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.func_222282_l(this);
		DefaultBiomeFeatures.func_222342_U(this);
		DefaultBiomeFeatures.func_222348_W(this);
		DefaultBiomeFeatures.func_222334_S(this);
		DefaultBiomeFeatures.func_222315_Z(this);
		DefaultBiomeFeatures.func_222292_ad(this);
		DefaultBiomeFeatures.addSprings(this);
		DefaultBiomeFeatures.func_222281_af(this);
		DefaultBiomeFeatures.func_222297_ap(this);
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
