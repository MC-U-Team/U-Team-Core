package info.u_team.u_team_core.intern.generation;

import java.util.Random;

import info.u_team.u_team_core.generation.GeneratableRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Internal world generator for Generation API
 * 
 * @author HyCraftHD
 * @date 21.10.2017
 */
public class WorldGenerator {
	
	public WorldGenerator() {
		GameRegistry.registerWorldGenerator(new WorldGeneratorFirst(), 0);
		GameRegistry.registerWorldGenerator(new WorldGeneratorLast(), Integer.MAX_VALUE);
	}
	
	private class WorldGeneratorFirst implements IWorldGenerator {
		
		@Override
		public void generate(Random random, int x, int y, World world, IChunkGenerator generator, IChunkProvider provider) {
			BlockPos chunkpos = new BlockPos(x * 16, 0, y * 16).add(8, 0, 8); // shift position to center
			GeneratableRegistry.generateFirst(world.provider.getDimension(), world, random, chunkpos);
		}
	}
	
	private class WorldGeneratorLast implements IWorldGenerator {
		
		@Override
		public void generate(Random random, int x, int y, World world, IChunkGenerator generator, IChunkProvider provider) {
			BlockPos chunkpos = new BlockPos(x * 16, 0, y * 16).add(8, 0, 8); // shift position to center
			GeneratableRegistry.generateLast(world.provider.getDimension(), world, random, chunkpos);
		}
	}
}
