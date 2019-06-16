package info.u_team.u_team_core.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.*;

/**
 * Basic implementation of {@link TileEntity} with some extra data synchronization methods.
 * 
 * @author HyCraftHD
 *
 */
public abstract class UTileEntity extends TileEntity {
	
	public UTileEntity(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		writeNBT(compound);
		return compound;
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		readNBT(compound);
	}
	
	/**
	 * Save data to disk. The {@link TileEntity#markDirty()} method must be called before.
	 * 
	 * @param compound
	 */
	public void writeNBT(CompoundNBT compound) {
	}
	
	/**
	 * Reads data from disk.
	 * 
	 * @param compound
	 */
	public void readNBT(CompoundNBT compound) {
	}
	
	// sync server -> client
	
	// synchronization on chunk load
	
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT compound = super.getUpdateTag();
		writeOnChunkLoadServer(compound);
		return compound;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleUpdateTag(CompoundNBT compound) {
		super.read(compound);
		readOnChunkLoadClient(compound);
	}
	
	/**
	 * Data here will be send to the client side when the chunk is loaded. The data is received in
	 * {@link UTileEntity#readOnChunkLoadClient(CompoundNBT)}
	 * 
	 * @param compound
	 */
	public void writeOnChunkLoadServer(CompoundNBT compound) {
	}
	
	/**
	 * The data from the chunk load is received here. The data is send from
	 * {@link UTileEntity#writeOnChunkLoadServer(CompoundNBT)}
	 * 
	 * @param compound
	 */
	@OnlyIn(Dist.CLIENT)
	public void readOnChunkLoadClient(CompoundNBT compound) {
	}
	
	// synchronization on block update
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		final CompoundNBT compound = new CompoundNBT();
		writeOnUpdateServer(compound);
		if (!compound.isEmpty()) {
			return new SUpdateTileEntityPacket(pos, -1, compound);
		}
		return null;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet) {
		readOnUpdateClient(packet.getNbtCompound());
	}
	
	/**
	 * Data here will be send to the client side when the block is updated. The data is received in
	 * {@link UTileEntity#readOnUpdateClient(CompoundNBT)}. To trigger an update call
	 * {@link World#notifyBlockUpdate(net.minecraft.util.math.BlockPos, BlockState, BlockState, int)} or
	 * {@link UTileEntity#sendChangesToClient(int)}
	 * 
	 * @param compound
	 */
	public void writeOnUpdateServer(CompoundNBT compound) {
	}
	
	/**
	 * The data from the block update is received here. The data is send from
	 * {@link UTileEntity#writeOnUpdateServer(CompoundNBT)}
	 * 
	 * @param compound
	 */
	@OnlyIn(Dist.CLIENT)
	public void readOnUpdateClient(CompoundNBT compound) {
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
		final BlockState state = getBlockState();
		world.notifyBlockUpdate(pos, state, state, flags);
	}
	
}
