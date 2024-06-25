package info.u_team.u_team_test.block;

import info.u_team.u_team_core.block.UEntityBlock;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

public class BasicFluidInventoryBlock extends UEntityBlock {
	
	public BasicFluidInventoryBlock() {
		super(Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).strength(1).requiresCorrectToolForDrops(), TestBlockEntityTypes.BASIC_FLUID_INVENTORY);
	}
	
	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		return openMenu(level, pos, player, true);
	}
	
}
