package info.u_team.u_team_test.dimension;

public class BasicDimension /*extends OverworldDimension*/ {
	// TODO wait for forge replacement
	/*public BasicDimension(World world, DimensionType type) {
		super(world, type);
	}
	
	@Override
	public ChunkGenerator<? extends GenerationSettings> createChunkGenerator() {
		final ChunkGeneratorType<OverworldGenSettings, OverworldChunkGenerator> chunkgenerator = ChunkGeneratorType.SURFACE;
		final BiomeProviderType<SingleBiomeProviderSettings, SingleBiomeProvider> biomeprovidertype = BiomeProviderType.FIXED;
		
		final OverworldGenSettings overworldgensettings = chunkgenerator.createSettings();
		final SingleBiomeProviderSettings overworldbiomeprovidersettings = biomeprovidertype.createSettings(world.getWorldInfo()).setBiome(TestBiomes.BASIC.get());
		return chunkgenerator.create(this.world, biomeprovidertype.create(overworldbiomeprovidersettings), overworldgensettings);
	}*/
	
}
