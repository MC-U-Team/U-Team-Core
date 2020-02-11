package info.u_team.u_team_test.dimension;

import info.u_team.u_team_test.init.TestBiomes;
import net.minecraft.world.World;
import net.minecraft.world.biome.provider.*;
import net.minecraft.world.dimension.*;
import net.minecraft.world.gen.*;

public class BasicDimension extends OverworldDimension {
	
	public BasicDimension(World world, DimensionType type) {
		super(world, type);
	}
	
	@Override
	public ChunkGenerator<? extends GenerationSettings> createChunkGenerator() {
		final ChunkGeneratorType<OverworldGenSettings, OverworldChunkGenerator> chunkgenerator = ChunkGeneratorType.SURFACE;
		final BiomeProviderType<SingleBiomeProviderSettings, SingleBiomeProvider> biomeprovidertype = BiomeProviderType.FIXED;
		
		final OverworldGenSettings overworldgensettings = chunkgenerator.createSettings();
		final SingleBiomeProviderSettings overworldbiomeprovidersettings = biomeprovidertype.func_226840_a_(world.getWorldInfo()).setBiome(TestBiomes.BASIC);
		return chunkgenerator.create(this.world, biomeprovidertype.create(overworldbiomeprovidersettings), overworldgensettings);
	}
	
}
