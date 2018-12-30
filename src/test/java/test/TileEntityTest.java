package test;

import java.util.Random;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.*;

public class TileEntityTest extends UTileEntity implements IInventory, ITickable, ISyncedContainerTileEntity {
	
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
	
	private Random random = new Random();
	private int serverTestInteger;
	
	@SideOnly(Side.CLIENT)
	int clientTestInteger;
	
	@Override
	public void update() {
		if (world.isRemote)
			return;
		if (random.nextInt(5) == 0) {
			serverTestInteger++;
			System.out.println(serverTestInteger);
			this.markDirty();
			
		}
	}
	
	@Override
	public void getServerSyncContainerData(NBTTagCompound compound) {
		compound.setInteger("int", serverTestInteger);
	}
	
	@Override
	public void handleClientSyncContainerData(NBTTagCompound compound) {
		clientTestInteger = compound.getInteger("int");
	}
	
	// TODO
	@Override
	public void getClientSyncContainerData(NBTTagCompound compound) {
		compound.setInteger("test", clientTestInteger);
	}
	
	@Override
	public void handleServerSyncContainerData(NBTTagCompound compound) {
		serverTestInteger = compound.getInteger("test");
	}
	
}
