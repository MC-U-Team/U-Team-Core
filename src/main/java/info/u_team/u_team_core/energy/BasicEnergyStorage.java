package info.u_team.u_team_core.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class BasicEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {

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

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void addEnergy(int energy) {
		this.energy += energy;
		if (this.energy > getMaxEnergyStored()) {
			this.energy = getEnergyStored();
		}
	}

	@Override
	public CompoundNBT serializeNBT() {
		final CompoundNBT compound = new CompoundNBT();
		compound.putInt("energy", getEnergyStored());
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT compound) {
		setEnergy(compound.getInt("energy"));
	}

}
