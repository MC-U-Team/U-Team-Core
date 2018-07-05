package info.u_team.u_team_core.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * TileEntity API<br>
 * -> Basic TileEntity
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public abstract class UTileEntity extends TileEntity {
	
	public UTileEntity() {
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readNBT(compound);
	}
	
	public abstract void readNBT(NBTTagCompound compound);
	
	public abstract void writeNBT(NBTTagCompound compound);
}
