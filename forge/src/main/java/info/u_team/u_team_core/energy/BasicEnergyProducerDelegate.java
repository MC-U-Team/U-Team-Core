package info.u_team.u_team_core.energy;

import net.minecraftforge.energy.IEnergyStorage;

public class BasicEnergyProducerDelegate implements IEnergyStorage {
	
	private final IEnergyStorage delegate;
	
	public BasicEnergyProducerDelegate(IEnergyStorage delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public boolean canExtract() {
		return true;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return delegate.extractEnergy(maxExtract, simulate);
	}
	
	@Override
	public int getEnergyStored() {
		return delegate.getEnergyStored();
	}
	
	@Override
	public int getMaxEnergyStored() {
		return delegate.getMaxEnergyStored();
	}
	
	@Override
	public boolean canReceive() {
		return false;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}
}
