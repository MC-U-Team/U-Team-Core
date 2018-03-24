package test;

import java.util.*;

import net.minecraft.world.World;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class Test {
	
	public static void generate(List<IWorldGenerator> sortedGeneratorList, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		long worldSeed = world.getSeed();
		Random fmlRandom = new Random(worldSeed);
		long xSeed = fmlRandom.nextLong() >> 2 + 1L;
		long zSeed = fmlRandom.nextLong() >> 2 + 1L;
		long chunkSeed = (xSeed * chunkX + zSeed * chunkZ) ^ worldSeed;
		
		for (IWorldGenerator generator : sortedGeneratorList) {
			fmlRandom.setSeed(chunkSeed);
			
			generator.generate(fmlRandom, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
		
	}
}
