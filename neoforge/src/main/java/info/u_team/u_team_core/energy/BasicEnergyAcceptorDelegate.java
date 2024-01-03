package info.u_team.u_team_core.energy;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class BasicEnergyAcceptorDelegate implements IEnergyStorage {
	
	private final IEnergyStorage delegate;
	
	public BasicEnergyAcceptorDelegate(IEnergyStorage delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public boolean canReceive() {
		return true;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return delegate.receiveEnergy(maxReceive, simulate);
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
	public boolean canExtract() {
		return false;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}
}
