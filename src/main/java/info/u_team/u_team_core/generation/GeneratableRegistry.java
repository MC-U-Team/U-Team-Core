package info.u_team.u_team_core.generation;

import java.util.*;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GeneratableRegistry {
	
	private static HashMap<Integer, ArrayList<IGeneratable>> generatablefirst = new HashMap<>(), generatablelast = new HashMap<>();
	
	public static void addFirst(int dimension, IGeneratable generatable) {
		generatablefirst.putIfAbsent(dimension, new ArrayList<>());
		generatablefirst.get(dimension).add(generatable);
	}
	
	public static void addLast(int dimension, IGeneratable generatable) {
		generatablelast.putIfAbsent(dimension, new ArrayList<>());
		generatablelast.get(dimension).add(generatable);
	}
	
	public static void generateFirst(int dimension, World world, Random random, BlockPos chunkpos) {
		if (generatablefirst.containsKey(dimension)) {
			generatablefirst.get(dimension).forEach(generatable -> generatable.generate(world, random, chunkpos));
		}
	}
	
	public static void generateLast(int dimension, World world, Random random, BlockPos chunkpos) {
		if (generatablelast.containsKey(dimension)) {
			generatablelast.get(dimension).forEach(generatable -> generatable.generate(world, random, chunkpos));
		}
	}
}
