package info.u_team.u_team_test.tileentity;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import info.u_team.u_team_core.tileentity.UTileEntity;
import info.u_team.u_team_test.container.BasicEnergyCreatorContainer;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicEnergyCreatorTileEntity extends UTileEntity implements IInitSyncedTileEntity, ITickableTileEntity {
	
	private final TileEntityUItemStackHandler slots;
	private final BasicEnergyStorage energy;
	
	private final LazyOptional<TileEntityUItemStackHandler> slotsOptional;
	private final LazyOptional<BasicEnergyStorage> energyOptional;
	
	public BasicEnergyCreatorTileEntity() {
		super(TestTileEntityTypes.BASIC_ENERGY_CREATOR.get());
		slots = new TileEntityUItemStackHandler(6, this) {
			
			@Override
			public int getSlotLimit(int slot) {
				return 16;
			}
		};
		energy = new BasicEnergyStorage(1000, 10);
		
		slotsOptional = LazyOptional.of(() -> slots);
		energyOptional = LazyOptional.of(() -> energy);
	}
	
	private boolean action = true;
	
	@Override
	public void tick() {
		if (world.isRemote) {
			return;
		}
		if (action) {
			energy.addEnergy(3);
			if (energy.getEnergyStored() == energy.getMaxEnergyStored()) {
				action = !action;
			}
		} else {
			energy.addEnergy(-3);
			if (energy.getEnergyStored() == 0) {
				action = !action;
			}
		}
		markDirty();
	}
	
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		buffer.writeInt(energy.getEnergyStored());
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		energy.setEnergy(buffer.readInt());
	}
	
	public TileEntityUItemStackHandler getSlots() {
		return slots;
	}
	
	public BasicEnergyStorage getEnergy() {
		return energy;
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		super.writeNBT(compound);
		compound.put("inventory", slots.serializeNBT());
		compound.put("energy", energy.serializeNBT());
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		super.readNBT(compound);
		slots.deserializeNBT(compound.getCompound("inventory"));
		energy.deserializeNBT(compound.getCompound("energy"));
	}
	
	// Capability
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return slotsOptional.cast();
		} else if (capability == CapabilityEnergy.ENERGY) {
			return energyOptional.cast();
		} else {
			return super.getCapability(capability, side);
		}
	}
	
	@Override
	public void remove() {
		super.remove();
		slotsOptional.invalidate();
		energyOptional.invalidate();
	}
	
	// Container
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new BasicEnergyCreatorContainer(id, playerInventory, this);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Energy creator");
	}
	
}
