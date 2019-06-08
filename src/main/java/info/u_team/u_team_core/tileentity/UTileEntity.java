package info.u_team.u_team_core.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.*;
import net.minecraftforge.api.distmarker.*;

public abstract class UTileEntity extends TileEntity {
	
	public UTileEntity(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		readNBT(compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		writeNBT(compound);
		return compound;
	}
	
	public void readNBT(CompoundNBT compound) {
	}
	
	public void writeNBT(CompoundNBT compound) {
	}
	
	// sync server -> client
	
	// synchronization on chunk load
	
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT compound = super.write(new CompoundNBT());
		writeOnChunkLoadServer(compound);
		return compound;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleUpdateTag(CompoundNBT compound) {
		super.read(compound);
		readOnChunkLoadClient(compound);
	}
	
	public void writeOnChunkLoadServer(CompoundNBT compound) {
	}
	
	@OnlyIn(Dist.CLIENT)
	public void readOnChunkLoadClient(CompoundNBT compound) {
	}
	
	// synchronization on block update
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT compound = new CompoundNBT();
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
	
	public void writeOnUpdateServer(CompoundNBT compound) {
	}
	
	@OnlyIn(Dist.CLIENT)
	public void readOnUpdateClient(CompoundNBT compound) {
	}
	
	public void sendChangesToClient() {
		sendChangesToClient(2);
	}
	
	public void sendChangesToClient(int flags) {
		final BlockState state = getBlockState();
		world.notifyBlockUpdate(pos, state, state, flags);
	}
	
}
