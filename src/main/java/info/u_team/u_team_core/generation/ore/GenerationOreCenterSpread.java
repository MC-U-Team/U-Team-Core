package info.u_team.u_team_core.generation.ore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenerationOreCenterSpread extends AbstractGenerationOre {
	
	protected int centerheight, spread;
	
	public GenerationOreCenterSpread(int veinsize, int count, int centerheight, int spread) {
		super(veinsize, count);
		this.centerheight = centerheight;
		this.spread = spread;
	}
	
	public GenerationOreCenterSpread(Block block, int veinsize, int count, int centerheight, int spread) {
		super(veinsize, count, block);
		this.centerheight = centerheight;
		this.spread = spread;
	}
	
	@Override
	public void generate(WorldGenerator generator, World world, Random random, BlockPos chunkpos) {
		for (int i = 0; i < count; ++i) {
			BlockPos blockpos = chunkpos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerheight - spread, random.nextInt(16));
			generator.generate(world, random, blockpos);
		}
	}
	
}
