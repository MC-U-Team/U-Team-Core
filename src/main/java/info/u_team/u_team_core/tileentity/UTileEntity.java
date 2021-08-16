package info.u_team.u_team_core.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Basic implementation of {@link TileEntity} with some extra data synchronization methods.
 *
 * @author HyCraftHD
 */
public abstract class UTileEntity extends BlockEntity {

	public UTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		super.save(compound);
		writeNBT(compound);
		return compound;
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		readNBT(compound);
	}

	/**
	 * Save data to disk. The {@link TileEntity#markDirty()} method must be called before.
	 *
	 * @param compound
	 */
	public void writeNBT(CompoundTag compound) {
	}

	/**
	 * Reads data from disk.
	 *
	 * @param state
	 * @param compound
	 */
	public void readNBT(CompoundTag compound) {
	}

	// sync server -> client

	// synchronization on chunk load

	@Override
	public CompoundTag getUpdateTag() {
		final var compound = super.getUpdateTag();
		sendChunkLoadData(compound);
		return compound;
	}

	@Override
	public void handleUpdateTag(CompoundTag compound) {
		super.load(compound);
		handleChunkLoadData(compound);
	}

	/**
	 * Data here will be send to the client side when the chunk is loaded. The data is received in
	 * {@link UTileEntity#handleChunkLoadData(CompoundNBT)}
	 *
	 * @param compound
	 */
	public void sendChunkLoadData(CompoundTag compound) {
	}

	/**
	 * The data from the chunk load is received here. The data is send from
	 * {@link UTileEntity#sendChunkLoadData(CompoundNBT)}
	 *
	 * @param compound
	 */
	public void handleChunkLoadData(CompoundTag compound) {
	}

	// synchronization on block update

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		final var compound = new CompoundTag();
		sendUpdateStateData(compound);
		if (!compound.isEmpty()) {
			return new ClientboundBlockEntityDataPacket(worldPosition, -1, compound);
		}
		return null;
	}

	@Override
	public void onDataPacket(Connection manager, ClientboundBlockEntityDataPacket packet) {
		handleUpdateStateData(packet.getTag());
	}

	/**
	 * Data here will be send to the client side when the block is updated. The data is received in
	 * {@link UTileEntity#handleUpdateStateData(CompoundNBT)}. To trigger an update call
	 * {@link World#notifyBlockUpdate(net.minecraft.util.math.BlockPos, BlockState, BlockState, int)} or
	 * {@link UTileEntity#sendChangesToClient(int)}
	 *
	 * @param compound
	 */
	public void sendUpdateStateData(CompoundTag compound) {
	}

	/**
	 * The data from the block update is received here. The data is send from
	 * {@link UTileEntity#sendUpdateStateData(CompoundNBT)}
	 *
	 * @param compound
	 */
	public void handleUpdateStateData(CompoundTag compound) {
	}

	/**
	 * Calls {@link UTileEntity#sendChangesToClient(int)} with flag 2 (send changes to client)
	 */
	public void sendChangesToClient() {
		sendChangesToClient(2);
	}

	/**
	 * Triggers a block update to send the data from the server to the client. For flags see here:
	 * {@link World#setBlockState(net.minecraft.util.math.BlockPos, BlockState, int)}
	 *
	 * @param flags Are described above
	 */
	public void sendChangesToClient(int flags) {
		final var state = getBlockState();
		level.sendBlockUpdated(worldPosition, state, state, flags);
	}

}
