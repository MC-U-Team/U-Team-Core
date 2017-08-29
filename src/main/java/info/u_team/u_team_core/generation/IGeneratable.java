package info.u_team.u_team_core.generation;

import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IGeneratable {
	
	public Map<BlockPos, IBlockState> getBlocks(World world, BlockPos start, BlockPos end);
	
}
