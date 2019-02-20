package info.u_team.u_team_test.tileentity;

import java.util.Iterator;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import info.u_team.u_team_core.tileentity.UTileEntityContainer;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.ContainerTileEntity;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

public class TileEntityTileEntity extends UTileEntityContainer implements IInventory, ISyncedContainerTileEntity, ITickable {
	
	private NonNullList<ItemStack> list;
	
	public TileEntityTileEntity() {
		super(TestTileEntityTypes.tileentity);
		list = NonNullList.withSize(18, ItemStack.EMPTY);
	}
	
	private int value;
	
	public int valueClient;
	
	@Override
	public void getServerSyncContainerData(NBTTagCompound compound) {
		compound.setInt("value", value);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleFromServerSyncContainerData(NBTTagCompound compound) {
		valueClient = compound.getInt("value");
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void getClientSyncContainerData(NBTTagCompound compound) {
		compound.setInt("value", valueClient);
	}
	
	@Override
	public void handleFromClientSyncContainerData(NBTTagCompound compound) {
		value = compound.getInt("value");
	}
	
	private int time;
	
	public void tick() {
		if (time < 20) {
			time++;
			return;
		}
		time = 0;
		value++;
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
		return new ContainerTileEntity(inventoryPlayer, this);
	}
	
	@Override
	public ResourceLocation getGui() {
		return new ResourceLocation(TestMod.modid, "tileentity");
	}
	
	@Override
	public void read(NBTTagCompound compound) {
		super.read(compound);
		System.out.println(Thread.currentThread().getName() + " : READ NBT HAS BEED CALLED!");
		System.out.println(compound);
		ItemStackHelper.loadAllItems(compound, list);
	}
	
	@Override
	public NBTTagCompound write(NBTTagCompound compound) {
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, list);
		System.out.println(Thread.currentThread().getName() + " : WRITE NBT HAS BEED CALLED!");
		System.out.println(compound);
		return compound;
	}
	
	@Override
	public void clear() {
		list.clear();
	}
	
	@Override
	public void closeInventory(EntityPlayer var1) {
	}
	
	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		ItemStack lvt_3_1_ = ItemStackHelper.getAndSplit(list, var1, var2);
		if (!lvt_3_1_.isEmpty()) {
			this.markDirty();
		}
		return lvt_3_1_;
	}
	
	@Override
	public int getField(int var1) {
		return 0;
	}
	
	@Override
	public int getFieldCount() {
		return 0;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public int getSizeInventory() {
		return list.size();
	}
	
	@Override
	public ItemStack getStackInSlot(int var1) {
		return var1 >= 0 && var1 < list.size() ? (ItemStack) list.get(var1) : ItemStack.EMPTY;
	}
	
	@Override
	public boolean isEmpty() {
		Iterator<ItemStack> var1 = list.iterator();
		
		ItemStack lvt_2_1_;
		do {
			if (!var1.hasNext()) {
				return true;
			}
			
			lvt_2_1_ = (ItemStack) var1.next();
		} while (lvt_2_1_.isEmpty());
		
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer var1) {
		return true;
	}
	
	@Override
	public void openInventory(EntityPlayer var1) {
	}
	
	@Override
	public ItemStack removeStackFromSlot(int var1) {
		ItemStack lvt_2_1_ = (ItemStack) this.list.get(var1);
		if (lvt_2_1_.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.list.set(var1, ItemStack.EMPTY);
			return lvt_2_1_;
		}
	}
	
	@Override
	public void setField(int var1, int var2) {
		
	}
	
	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		this.list.set(var1, var2);
		if (!var2.isEmpty() && var2.getCount() > this.getInventoryStackLimit()) {
			var2.setCount(this.getInventoryStackLimit());
		}
		
		this.markDirty();
		
	}
	
}
