package test;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.*;

public class TileEntityTest extends UTileEntity {
	
	@SideOnly(Side.CLIENT)
	public int clientInt;
	
	public int serverInt;
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		serverInt = compound.getInteger("server");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInteger("server", serverInt);
	}
	
	@Override
	public void getServerSyncData(NBTTagCompound compound) {
		compound.setInteger("transfer", serverInt);
	}
	
	@Override
	public void handleClientSyncData(NBTTagCompound compound) {
		clientInt = compound.getInteger("transfer");
	}
	
	public void increment() {
		System.out.println("increment");
		serverInt++;
	}
	
	public void save() {
		System.out.println("save");
		sendChangesToClient();
	}
	
}
