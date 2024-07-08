package info.u_team.u_team_core.energy;

import info.u_team.u_team_core.api.sync.DataHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

public class BasicEnergyStorage extends EnergyStorage {
	
	public BasicEnergyStorage(int capacity) {
		this(capacity, capacity, capacity, 0);
	}
	
	public BasicEnergyStorage(int capacity, int maxTransfer) {
		this(capacity, maxTransfer, maxTransfer, 0);
	}
	
	public BasicEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		this(capacity, maxReceive, maxExtract, 0);
	}
	
	public BasicEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		final int value = super.extractEnergy(maxExtract, simulate);
		if (!simulate) {
			onEnergyChanged();
		}
		return value;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		final int value = super.receiveEnergy(maxReceive, simulate);
		if (!simulate) {
			onEnergyChanged();
		}
		return value;
	}
	
	public int getEnergy() {
		return super.getEnergyStored();
	}
	
	public void setEnergy(int energy) {
		this.energy = energy;
		onEnergyChanged();
	}
	
	public void addEnergy(int energy) {
		this.energy += energy;
		if (this.energy > getMaxEnergyStored()) {
			this.energy = getMaxEnergyStored();
		} else if (this.energy < 0) {
			this.energy = 0;
		}
		onEnergyChanged();
	}
	
	public void removeEnergy(int energy) {
		addEnergy(-energy);
	}
	
	public void onEnergyChanged() {
	}
	
	@Override
	public CompoundTag serializeNBT(HolderLookup.Provider registries) {
		final CompoundTag compound = new CompoundTag();
		compound.putInt("energy", getEnergyStored());
		return compound;
	}
	
	@Override
	public void deserializeNBT(HolderLookup.Provider registries, Tag tag) {
		if (tag instanceof final CompoundTag compoundTag) {
			setEnergy(compoundTag.getInt("energy"));
		} else {
			super.deserializeNBT(registries, tag);
		}
	}
	
	public DataHolder createSyncHandler() {
		return DataHolder.createIntHolder(this::getEnergy, this::setEnergy);
	}
	
}
