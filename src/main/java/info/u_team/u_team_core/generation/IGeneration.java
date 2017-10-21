package info.u_team.u_team_core.generation;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * Generation API<br>
 * -> Generation implementation
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public interface IGeneration {
	
	public void generate(WorldGenerator generator, World world, Random random, BlockPos chunkpos);
	
}
