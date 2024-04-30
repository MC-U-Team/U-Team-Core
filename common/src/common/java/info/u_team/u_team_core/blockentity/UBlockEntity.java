package info.u_team.u_team_core.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Basic implementation of {@link BlockEntity} with some extra data synchronization methods.
 *
 * @author HyCraftHD
 */
public abstract class UBlockEntity extends BlockEntity implements SyncedBlockEntity {
	
	public UBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	@Override
	public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		saveNBT(tag, registries);
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
		loadNBT(tag, registries);
	}
	
	/**
	 * Save data to disk. To mark the block entity to save data {@link #setChanged()} must be called.
	 *
	 * @param tag
	 */
	public void saveNBT(CompoundTag tag, HolderLookup.Provider registries) {
	}
	
	/**
	 * Reads data from disk.
	 *
	 * @param tag
	 */
	public void loadNBT(CompoundTag tag, HolderLookup.Provider registries) {
	}
	
	// synchronization on chunk load
	
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		final CompoundTag tag = new CompoundTag();
		sendChunkLoadData(tag);
		return tag;
	}
	
	public void receiveUpdateTag(CompoundTag tag) {
		handleChunkLoadData(tag);
	}
	
	// synchronization on block update
	
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		final CompoundTag tag = new CompoundTag();
		sendUpdateStateData(tag);
		return ClientboundBlockEntityDataPacket.create(this, (blockEntity, provider) -> tag);
	}
	
	public void receiveUpdatePacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
		final CompoundTag tag = packet.getTag();
		handleUpdateStateData(tag == null ? new CompoundTag() : tag);
	}
	
	/**
	 * Calls {@link UBlockEntity#sendChangesToClient(int)} with flag 2 (send changes to client)
	 */
	public void sendChangesToClient() {
		sendChangesToClient(2);
	}
	
	/**
	 * Triggers a block update to send the data from the server to the client. For flags see here:
	 * {@link Level#setBlock(BlockPos, BlockState, int)}
	 *
	 * @param flags Are described above
	 */
	public void sendChangesToClient(int flags) {
		final BlockState state = getBlockState();
		level.sendBlockUpdated(worldPosition, state, state, flags);
	}
	
}

/**
 * Interface to hold synced methods
 */
interface SyncedBlockEntity {
	
	/**
	 * Data here will be send to the client side when the chunk is loaded. The data is received in
	 * {@link SyncedBlockEntity#handleChunkLoadData(CompoundNBT)}
	 *
	 * @param tag
	 */
	default void sendChunkLoadData(CompoundTag tag) { // TODO add registry provider
	}
	
	/**
	 * The data from the chunk load is received here. The data is send from
	 * {@link SyncedBlockEntity#sendChunkLoadData(CompoundNBT)}
	 *
	 * @param tag
	 */
	default void handleChunkLoadData(CompoundTag tag) {
	}
	
	/**
	 * Data here will be send to the client side when the block is updated. The data is received in
	 * {@link SyncedBlockEntity#handleUpdateStateData(CompoundNBT)}. To trigger an update call
	 * {@link Level#sendBlockUpdated(BlockPos, BlockState, BlockState, int)} or
	 * {@link UBlockEntity#sendChangesToClient(int)}
	 *
	 * @param tag
	 */
	default void sendUpdateStateData(CompoundTag tag) {
	}
	
	/**
	 * The data from the block update is received here. The data is send from
	 * {@link SyncedBlockEntity#sendUpdateStateData(CompoundNBT)}
	 *
	 * @param tag
	 */
	default void handleUpdateStateData(CompoundTag tag) {
	}
	
}
