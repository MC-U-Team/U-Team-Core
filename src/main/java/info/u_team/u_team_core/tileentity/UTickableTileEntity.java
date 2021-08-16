package info.u_team.u_team_core.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class UTickableTileEntity extends UTileEntity {

	private boolean first;

	public UTickableTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	// TODO do we need this class anymore?

	// @Override
	// public void tick() {
	// if (!first) {
	// first = true;
	// firstTick();
	// }
	// if (level.isClientSide()) {
	// tickClient();
	// } else {
	// tickServer();
	// }
	// }

	// protected void firstTick() {
	// }
	//
	// protected void tickServer() {
	// }
	//
	// protected void tickClient() {
	// }

}
