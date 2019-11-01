package info.u_team.u_team_test.container;

import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import info.u_team.u_team_core.container.UTileEntityContainer;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicEnergyCreatorTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class BasicEnergyCreatorContainer extends UTileEntityContainer<BasicEnergyCreatorTileEntity> {
	
	public BasicEnergyCreatorContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(TestContainers.BASIC_ENERGY_CREATOR, id, playerInventory, buffer);
	}
	
	public BasicEnergyCreatorContainer(int id, PlayerInventory playerInventory, BasicEnergyCreatorTileEntity tileEntity) {
		super(TestContainers.BASIC_ENERGY_CREATOR, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		tileEntity.getSlots().ifPresent(handler -> appendInventory(handler, 2, 3, 116, 41));
		appendPlayerInventory(playerInventory, 8, 91);
		addServerToClientTracker(BufferReferenceHolder.createIntHolder(() -> BasicEnergyStorage.getTileEntityEnergy(tileEntity), value -> BasicEnergyStorage.setTileEntityEnergy(tileEntity, value)));
	}
}
