package info.u_team.u_team_core.generation;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * Generation API<br>
 * -> Generation Settings
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public interface IGeneratable {
	
	public IGeneration getGeneration();
	
	public WorldGenerator getGenerator();
	
	default void generate(World world, Random random, BlockPos chunkpos) {
		getGeneration().generate(getGenerator(), world, random, chunkpos);
	}
	
}
