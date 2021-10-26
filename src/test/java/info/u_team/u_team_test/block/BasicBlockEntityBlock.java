package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UEntityBlock;
import info.u_team.u_team_test.blockentity.BasicBlockEntityBlockEntity;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import info.u_team.u_team_test.init.TestCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class BasicBlockEntityBlock extends UEntityBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	
	public BasicBlockEntityBlock() {
		super(TestCreativeTabs.TAB, Properties.of(Material.STONE).strength(2F).requiresCorrectToolForDrops().sound(SoundType.GRAVEL).friction(0.8F).lightLevel(state -> 1), TestBlockEntityTypes.BASIC);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return openMenu(world, pos, player, true);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if ((type != tileEntityType.get()) || level.isClientSide()) {
			return null;
		}
		return (level_, pos, state_, instance) -> BasicBlockEntityBlockEntity.serverTick(level_, pos, state_, (BasicBlockEntityBlockEntity) instance);
	}
	
}
