package info.u_team.u_team_core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Basic implementation of {@link BlockEntity} with some extra data synchronization methods.
 *
 * @author HyCraftHD
 */
public abstract class UBlockEntity extends BlockEntity {
	
	public UBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	@Override
	public CompoundTag save(CompoundTag tag) {
		super.save(tag);
		saveNBT(tag);
		return tag;
	}
	
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		loadNBT(tag);
	}
	
	/**
	 * Save data to disk. The {@link #setChanged()} method must be called before.
	 *
	 * @param tag
	 */
	public void saveNBT(CompoundTag tag) {
	}
	
	/**
	 * Reads data from disk.
	 *
	 * @param state
	 * @param tag
	 */
	public void loadNBT(CompoundTag tag) {
	}
	
	// sync server -> client
	
	// synchronization on chunk load
	
	@Override
	public CompoundTag getUpdateTag() {
		final var tag = super.getUpdateTag();
		sendChunkLoadData(tag);
		return tag;
	}
	
	@Override
	public void handleUpdateTag(CompoundTag tag) {
		super.load(tag);
		handleChunkLoadData(tag);
	}
	
	/**
	 * Data here will be send to the client side when the chunk is loaded. The data is received in
	 * {@link UBlockEntity#handleChunkLoadData(CompoundNBT)}
	 *
	 * @param tag
	 */
	public void sendChunkLoadData(CompoundTag tag) {
	}
	
	/**
	 * The data from the chunk load is received here. The data is send from
	 * {@link UBlockEntity#sendChunkLoadData(CompoundNBT)}
	 *
	 * @param tag
	 */
	public void handleChunkLoadData(CompoundTag tag) {
	}
	
	// synchronization on block update
	
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		final var tag = new CompoundTag();
		sendUpdateStateData(tag);
		if (!tag.isEmpty()) {
			return new ClientboundBlockEntityDataPacket(worldPosition, -1, tag);
		}
		return null;
	}
	
	@Override
	public void onDataPacket(Connection manager, ClientboundBlockEntityDataPacket packet) {
		handleUpdateStateData(packet.getTag());
	}
	
	/**
	 * Data here will be send to the client side when the block is updated. The data is received in
	 * {@link UBlockEntity#handleUpdateStateData(CompoundNBT)}. To trigger an update call
	 * {@link World#notifyBlockUpdate(net.minecraft.util.math.BlockPos, BlockState, BlockState, int)} or
	 * {@link UBlockEntity#sendChangesToClient(int)}
	 *
	 * @param tag
	 */
	public void sendUpdateStateData(CompoundTag tag) {
	}
	
	/**
	 * The data from the block update is received here. The data is send from
	 * {@link UBlockEntity#sendUpdateStateData(CompoundNBT)}
	 *
	 * @param tag
	 */
	public void handleUpdateStateData(CompoundTag tag) {
	}
	
	/**
	 * Calls {@link UBlockEntity#sendChangesToClient(int)} with flag 2 (send changes to client)
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
