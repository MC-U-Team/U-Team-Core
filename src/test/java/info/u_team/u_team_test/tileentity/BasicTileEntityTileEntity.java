package info.u_team.u_team_test.tileentity;

import java.util.Iterator;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import info.u_team.u_team_core.container.USyncedTileEntityContainer;
import info.u_team.u_team_core.tileentity.UTileEntity;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.*;

public class BasicTileEntityTileEntity extends UTileEntity implements IInventory, ISyncedContainerTileEntity, ITickableTileEntity {
	
	private final NonNullList<ItemStack> list;
	
	public int cooldown, value;
	
	public BasicTileEntityTileEntity() {
		super(TestTileEntityTypes.tileentity);
		list = NonNullList.withSize(18, ItemStack.EMPTY);
	}
	
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		final String data = "HELLO WORLD";
		System.out.println("SEND DATA: " + data);
		buffer.writeString(data);
	}
	
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		final String data = buffer.readString();
		System.out.println("RECEIVE DATA: " + data);
	}
	
	@Override
	public USyncedTileEntityContainer<?> createMenu(int windowid, PlayerInventory playerInventory, PlayerEntity player) {
		return new BasicTileEntityContainer(windowid, playerInventory, this);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Basic inventory");
	}
	
	// @Override
	// public void writeOnContainerSyncServer(CompoundNBT compound) {
	// compound.putInt("value", value);
	// compound.putInt("cooldown", cooldown);
	// }
	//
	// @OnlyIn(Dist.CLIENT)
	// @Override
	// public void readOnContainerSyncClient(CompoundNBT compound) {
	// value = compound.getInt("value");
	// cooldown = compound.getInt("cooldown");
	// }
	//
	// @OnlyIn(Dist.CLIENT)
	// @Override
	// public void writeOnContainerSyncClient(CompoundNBT compound) {
	// compound.putInt("value", value);
	// compound.putInt("cooldown", cooldown);
	// }
	//
	// @Override
	// public void readOnContainerSyncServer(CompoundNBT compound) {
	// value = compound.getInt("value");
	// cooldown = Math.min(compound.getInt("cooldown"), 100);
	// markDirty();
	// }
	
	private int timer;
	
	@Override
	public void tick() {
		if (world.isRemote) {
			return;
		}
		
		if (timer < cooldown) {
			timer++;
			return;
		}
		timer = 0;
		value++;
		markDirty();
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		ItemStackHelper.loadAllItems(compound, list);
		value = compound.getInt("value");
		cooldown = compound.getInt("cooldown");
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		ItemStackHelper.saveAllItems(compound, list);
		compound.putInt("value", value);
		compound.putInt("cooldown", cooldown);
	}
	
	@Override
	public void clear() {
		list.clear();
	}
	
	@Override
	public void closeInventory(PlayerEntity var1) {
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
			
			lvt_2_1_ = var1.next();
		} while (lvt_2_1_.isEmpty());
		
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}
	
	@Override
	public boolean isUsableByPlayer(PlayerEntity var1) {
		return true;
	}
	
	@Override
	public void openInventory(PlayerEntity var1) {
	}
	
	@Override
	public ItemStack removeStackFromSlot(int var1) {
		ItemStack lvt_2_1_ = this.list.get(var1);
		if (lvt_2_1_.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.list.set(var1, ItemStack.EMPTY);
			return lvt_2_1_;
		}
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
