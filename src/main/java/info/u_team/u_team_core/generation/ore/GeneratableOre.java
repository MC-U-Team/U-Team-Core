package info.u_team.u_team_core.generation.ore;

import info.u_team.u_team_core.generation.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.feature.*;

public class GeneratableOre implements IGeneratable {
	
	private WorldGenerator generator;
	private AbstractGenerationOre settings;
	
	public GeneratableOre(IBlockState blockstate, AbstractGenerationOre settings) {
		this(new WorldGenMinable(blockstate, settings.veinsize, settings.predicate), settings);
	}
	
	public GeneratableOre(WorldGenerator generator, AbstractGenerationOre settings) {
		this.generator = generator;
		this.settings = settings;
	}
	
	@Override
	public WorldGenerator getGenerator() {
		return generator;
	}
	
	@Override
	public IGeneration getGeneration() {
		return settings;
	}
}
