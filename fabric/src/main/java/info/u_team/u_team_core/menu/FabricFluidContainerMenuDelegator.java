package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.menu.FluidContainerMenu.FluidContainerDelegator;

// Unstable, Not implemented yet
public class FabricFluidContainerMenuDelegator implements FluidContainerDelegator {
	
	FabricFluidContainerMenuDelegator(FluidContainerMenu menu) {
	}
	
	@Override
	public void sendAllDataToRemote() {
	}
	
	@Override
	public void broadcastChanges() {
	}
	
	@Override
	public void broadcastFullState() {
	}
	
	public static class Factory implements FluidContainerDelegator.Factory {
		
		@Override
		public FluidContainerDelegator create(FluidContainerMenu menu) {
			return new FabricFluidContainerMenuDelegator(menu);
		}
	}
}
