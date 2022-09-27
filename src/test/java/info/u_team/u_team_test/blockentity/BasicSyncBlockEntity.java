package info.u_team.u_team_test.blockentity;

import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BasicSyncBlockEntity extends UBlockEntity {
	
	public BasicSyncBlockEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC_SYNC.get(), pos, state);
	}
	
}
