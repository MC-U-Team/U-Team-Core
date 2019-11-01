package info.u_team.u_team_test.container;

import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import info.u_team.u_team_core.container.UTileEntityContainer;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicTileEntityTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class BasicTileEntityContainer extends UTileEntityContainer<BasicTileEntityTileEntity> {
	
	// Client
	public BasicTileEntityContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(TestContainers.BASIC, id, playerInventory, buffer);
	}
	
	// Server
	public BasicTileEntityContainer(int id, PlayerInventory playerInventory, BasicTileEntityTileEntity tileEntity) {
		super(TestContainers.BASIC, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		tileEntity.getSlots().ifPresent(handler -> appendInventory(handler, 2, 9, 8, 41));
		appendPlayerInventory(playerInventory, 8, 91);
		
		addServerToClientTracker(BufferReferenceHolder.createIntHolder(() -> tileEntity.value, value -> tileEntity.value = value));
		addServerToClientTracker(BufferReferenceHolder.createIntHolder(() -> tileEntity.cooldown, value -> tileEntity.cooldown = value));
		
		addClientToServerTracker(BufferReferenceHolder.createIntHolder(() -> tileEntity.value, value -> {
			tileEntity.value = value;
			tileEntity.markDirty();
		}));
		addClientToServerTracker(BufferReferenceHolder.createIntHolder(() -> tileEntity.cooldown, value -> {
			tileEntity.cooldown = Math.min(value, 100);
			tileEntity.markDirty();
		}));
	}
}
