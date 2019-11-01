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
	
	private final LazyOptional<TileEntityUItemStackHandler> slots = LazyOptional.of(() -> new TileEntityUItemStackHandler(6, this) {
		
		public int getSlotLimit(int slot) {
			return 16;
		}
	});
	
	private final LazyOptional<BasicEnergyStorage> energy = LazyOptional.of(() -> new BasicEnergyStorage(1000, 10));
	
	public BasicEnergyCreatorTileEntity() {
		super(TestTileEntityTypes.BASIC_ENERGY_CREATOR);
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		slots.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("inventory")));
		energy.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("energy")));
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		slots.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));
		energy.ifPresent(handler -> compound.put("energy", handler.serializeNBT()));
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return slots.cast();
		}
		if (capability == CapabilityEnergy.ENERGY) {
			return energy.cast();
		}
		return super.getCapability(capability, side);
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new BasicEnergyCreatorContainer(id, playerInventory, this);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Energy creator");
	}
	
	private boolean action = true;
	
	@Override
	public void tick() {
		if (world.isRemote) {
			return;
		}
		energy.ifPresent(handler -> {
			if (action) {
				handler.addEnergy(3);
				if (handler.getEnergyStored() == handler.getMaxEnergyStored()) {
					action = !action;
				}
			} else {
				handler.addEnergy(-3);
				if (handler.getEnergyStored() == 0) {
					action = !action;
				}
			}
			markDirty();
		});
	}
	
	@Override
	public void sendInitialDataBuffer(PacketBuffer buffer) {
		energy.ifPresent(handler -> buffer.writeInt(handler.getEnergyStored()));
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(PacketBuffer buffer) {
		energy.ifPresent(handler -> handler.setEnergy(buffer.readInt()));
	}
	
	public LazyOptional<TileEntityUItemStackHandler> getSlots() {
		return slots;
	}
}
