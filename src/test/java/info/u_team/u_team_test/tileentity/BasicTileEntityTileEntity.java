package info.u_team.u_team_test.tileentity;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import info.u_team.u_team_core.tileentity.UTileEntity;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicTileEntityTileEntity extends UTileEntity implements IInitSyncedTileEntity, ITickableTileEntity {
	
	private final LazyOptional<TileEntityUItemStackHandler> slots = LazyOptional.of(() -> new TileEntityUItemStackHandler(18, this));
	
	public int cooldown, value;
	
	public BasicTileEntityTileEntity() {
		super(TestTileEntityTypes.BASIC);
	}
	
	@Override
	public Container createMenu(int windowid, PlayerInventory playerInventory, PlayerEntity player) {
		return new BasicTileEntityContainer(windowid, playerInventory, this);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Tile Entity");
	}
	
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		buffer.writeInt(value);
		buffer.writeInt(cooldown);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		value = buffer.readInt();
		cooldown = buffer.readInt();
	}
	
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
		slots.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("inventory")));
		value = compound.getInt("value");
		cooldown = compound.getInt("cooldown");
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		slots.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));
		compound.putInt("value", value);
		compound.putInt("cooldown", cooldown);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return slots.cast();
		}
		return super.getCapability(capability, side);
	}
	
	public LazyOptional<TileEntityUItemStackHandler> getSlots() {
		return slots;
	}
	
	@Override
	public void remove() {
		slots.invalidate();
		super.remove();
	}
}
