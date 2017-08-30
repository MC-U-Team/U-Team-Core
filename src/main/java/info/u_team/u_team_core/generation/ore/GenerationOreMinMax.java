package info.u_team.u_team_core.generation.ore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenerationOreMinMax extends AbstractGenerationOre {
	
	protected int minheight, maxheight;
	
	public GenerationOreMinMax(int veinsize, int count, int minheight, int maxheight) {
		super(veinsize, count);
		this.minheight = minheight;
		this.maxheight = maxheight;
	}
	
	public GenerationOreMinMax(Block block, int veinsize, int count, int minheight, int maxheight) {
		super(veinsize, count, block);
		this.minheight = minheight;
		this.maxheight = maxheight;
	}
	
	@Override
	public void generate(WorldGenerator generator, World world, Random random, BlockPos chunkpos) {
		if (maxheight < minheight) {
			int i = minheight;
			minheight = maxheight;
			maxheight = i;
		} else if (maxheight == minheight) {
			if (minheight < 255) {
				++maxheight;
			} else {
				--minheight;
			}
		}
		
		for (int j = 0; j < count; ++j) {
			BlockPos blockpos = chunkpos.add(random.nextInt(16), random.nextInt(maxheight - minheight) + minheight, random.nextInt(16));
			generator.generate(world, random, blockpos);
		}
	}
	
}
