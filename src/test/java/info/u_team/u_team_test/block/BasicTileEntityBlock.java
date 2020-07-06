package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UTileEntityBlock;
import info.u_team.u_team_test.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class BasicTileEntityBlock extends UTileEntityBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public BasicTileEntityBlock() {
		super(TestItemGroups.GROUP, Properties.create(Material.ROCK).hardnessAndResistance(2F).sound(SoundType.GROUND).slipperiness(0.8F).func_235838_a_(state -> 1), TestTileEntityTypes.BASIC);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return openContainer(world, pos, player, true);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
}
