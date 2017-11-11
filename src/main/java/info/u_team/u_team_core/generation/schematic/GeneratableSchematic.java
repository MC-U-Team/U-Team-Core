package info.u_team.u_team_core.generation.schematic;

import info.u_team.u_team_core.generation.*;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * Generation API<br>
 * -> Generatable Schematic
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class GeneratableSchematic implements IGeneratable {
	
	private AbstractGenerationSchematic generation;
	
	public GeneratableSchematic(AbstractGenerationSchematic generation) {
		this.generation = generation;
	}
	
	@Override
	public IGeneration getGeneration() {
		return generation;
	}
	
	@Override
	public WorldGenerator getGenerator() {
		return new WorldGenSchematic(generation.url, generation.centerstart, generation.rotation);
	}
	
}
