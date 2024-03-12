package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UEntityBlock;
import info.u_team.u_team_test.blockentity.BasicEnergyCreatorBlockEntity;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import info.u_team.u_team_test.init.TestCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class BasicEnergyCreatorBlock extends UEntityBlock {
	
	public BasicEnergyCreatorBlock() {
		super(TestCreativeTabs.TAB, Properties.of(Material.METAL).strength(2).requiresCorrectToolForDrops(), TestBlockEntityTypes.BASIC_ENERGY_CREATOR);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return openMenu(world, pos, player, true);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if ((type != tileEntityType.get()) || level.isClientSide()) {
			return null;
		}
		return (level_, pos, state_, instance) -> BasicEnergyCreatorBlockEntity.serverTick(level_, pos, state_, (BasicEnergyCreatorBlockEntity) instance);
	}
	
}
