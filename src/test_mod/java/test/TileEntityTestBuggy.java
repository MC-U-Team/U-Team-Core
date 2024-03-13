package test;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.*;

public class TileEntityTestBuggy extends UTileEntity implements IInventory, ITickable, ISyncedContainerTileEntity {
	
	@Override
	public void getServerSyncData(NBTTagCompound compound) {
	}
	
	@Override
	public void handleClientSyncData(NBTTagCompound compound) {
	}
	
	@Override
	public String getName() {
		return "test";
	}
	
	@Override
	public boolean hasCustomName() {
		return false;
	}
	
	@Override
	public int getSizeInventory() {
		return 0;
	}
	
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 0;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}
	
	@Override
	public void openInventory(EntityPlayer player) {
	}
	
	@Override
	public void closeInventory(EntityPlayer player) {
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}
	
	@Override
	public void clear() {
	}
	
	@Override
	public int getField(int id) {
		return 0;
	}
	
	@Override
	public void setField(int id, int value) {
	}
	
	@Override
	public int getFieldCount() {
		return 0;
	}
	
	@Override
	public void update() {
		// Nothing
		if (world.rand.nextInt(10) == 0) {
			if (!world.isRemote)
				System.out.println(myvalue);
		}
	}
	
	int myvalue;
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		myvalue = compound.getInteger("myvalue");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		compound.setInteger("myvalue", myvalue);
	}
	
	// Server -> Client
	
	@Override
	public void getServerSyncContainerData(NBTTagCompound compound) {
		
		compound.setInteger("myvalue", myvalue);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void handleFromServerSyncContainerData(NBTTagCompound compound) {
		myvalue = compound.getInteger("myvalue");
	}
	
	// Client -> Server
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getClientSyncContainerData(NBTTagCompound compound) {
		compound.setInteger("myvalue", myvalue);
	}
	
	@Override
	public void handleFromClientSyncContainerData(NBTTagCompound compound) {
		myvalue = compound.getInteger("myvalue");
	}
}
