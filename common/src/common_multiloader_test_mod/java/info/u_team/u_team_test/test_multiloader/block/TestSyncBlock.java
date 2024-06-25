package info.u_team.u_team_test.test_multiloader.block;

import java.util.Optional;

import info.u_team.u_team_core.block.UEntityBlock;
import info.u_team.u_team_test.test_multiloader.blockentity.TestSyncBlockEntity;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class TestSyncBlock extends UEntityBlock {
	
	public TestSyncBlock() {
		super(Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).sound(SoundType.METAL).strength(2).requiresCorrectToolForDrops(), TestMultiLoaderBlockEntityTypes.TEST_SYNC);
	}
	
	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (level.isClientSide() || !(player instanceof ServerPlayer)) {
			return InteractionResult.SUCCESS;
		}
		
		final Optional<TestSyncBlockEntity> blockEntityOptional = getBlockEntity(level, pos);
		if (!blockEntityOptional.isPresent()) {
			return InteractionResult.PASS;
		}
		
		final TestSyncBlockEntity blockEntity = blockEntityOptional.get();
		blockEntity.triggerCounter();
		return InteractionResult.SUCCESS;
	}
	
}
