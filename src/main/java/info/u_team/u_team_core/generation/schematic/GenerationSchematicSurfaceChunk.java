package info.u_team.u_team_core.generation.schematic;

import java.net.URL;
import java.util.Random;

import info.u_team.u_team_core.schematic.USchematicRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * Generation API<br>
 * -> Schematic Generation per chunk surface chance from 0 - 1
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */

public class GenerationSchematicSurfaceChunk extends AbstractGenerationSchematic {
	
	protected float chanceperchunk;
	
	public GenerationSchematicSurfaceChunk(URL url, boolean centerstart, USchematicRotation rotation, float chanceperchunk) {
		super(url, centerstart, rotation);
		this.chanceperchunk = chanceperchunk;
	}
	
	public GenerationSchematicSurfaceChunk(URL url, boolean centerstart, float chanceperchunk) {
		super(url, centerstart);
		this.chanceperchunk = chanceperchunk;
	}
	
	public GenerationSchematicSurfaceChunk(URL url, float chanceperchunk) {
		super(url);
		this.chanceperchunk = chanceperchunk;
	}
	
	@Override
	public void generate(WorldGenerator generator, World world, Random random, BlockPos chunkpos) {
		if (random.nextFloat() <= chanceperchunk) {
			BlockPos blockpos = world.getTopSolidOrLiquidBlock(chunkpos.add(random.nextInt(16), 0, random.nextInt(16))).add(8, 0, 8);
			generator.generate(world, random, blockpos);
		}
	}
	
}
