package info.u_team.u_team_core.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.*;
import net.minecraftforge.api.distmarker.*;

public abstract class UTileEntity extends TileEntity {
	
	public UTileEntity(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);
		readNBT(compound);
	}
	
	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		super.write(compound);
		writeNBT(compound);
		return compound;
	}
	
	public void readNBT(NBTTagCompound compound) {
	}
	
	public void writeNBT(NBTTagCompound compound) {
	}
	
	// sync server -> client
	
	// synchronization on chunk load
	
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound compound = super.write(new NBTTagCompound());
		writeOnChunkLoadServer(compound);
		return compound;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleUpdateTag(NBTTagCompound compound) {
		super.read(compound);
		readOnChunkLoadClient(compound);
	}
	
	public void writeOnChunkLoadServer(NBTTagCompound compound) {
	}
	
	@OnlyIn(Dist.CLIENT)
	public void readOnChunkLoadClient(NBTTagCompound compound) {
	}
	
	// synchronization on block update
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound compound = new NBTTagCompound();
		writeOnUpdateServer(compound);
		if (!compound.isEmpty()) {
			return new SPacketUpdateTileEntity(pos, -1, compound);
		}
		return null;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		readOnUpdateClient(packet.getNbtCompound());
	}
	
	public void writeOnUpdateServer(NBTTagCompound compound) {
	}
	
	@OnlyIn(Dist.CLIENT)
	public void readOnUpdateClient(NBTTagCompound compound) {
	}
	
	public void sendChangesToClient() {
		sendChangesToClient(2);
	}
	
	public void sendChangesToClient(int flags) {
		IBlockState state = getBlockState();
		world.notifyBlockUpdate(pos, state, state, flags);
	}
	
}
