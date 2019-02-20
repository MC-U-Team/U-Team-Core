package info.u_team.u_team_core.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.*;

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
	
}
