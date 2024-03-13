package info.u_team.u_team_test.container;

import info.u_team.u_team_core.api.sync.*;
import info.u_team.u_team_core.api.sync.MessageHolder.EmptyMessageHolder;
import info.u_team.u_team_core.container.UTileEntityContainer;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicTileEntityTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class BasicTileEntityContainer extends UTileEntityContainer<BasicTileEntityTileEntity> {
	
	private EmptyMessageHolder valueMessage;
	private MessageHolder cooldownMessage;
	
	// Client
	public BasicTileEntityContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(TestContainers.BASIC.get(), id, playerInventory, buffer);
	}
	
	// Server
	public BasicTileEntityContainer(int id, PlayerInventory playerInventory, BasicTileEntityTileEntity tileEntity) {
		super(TestContainers.BASIC.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
		appendInventory(tileEntity.getSlots(), 2, 9, 8, 41);
		appendPlayerInventory(playerInventory, 8, 91);
		
		addServerToClientTracker(BufferReferenceHolder.createIntHolder(() -> tileEntity.value, value -> tileEntity.value = value));
		addServerToClientTracker(BufferReferenceHolder.createIntHolder(() -> tileEntity.cooldown, value -> tileEntity.cooldown = value));
		
		valueMessage = addClientToServerTracker(new EmptyMessageHolder(() -> {
			tileEntity.value += 100;
			tileEntity.markDirty();
		}));
		
		cooldownMessage = addClientToServerTracker(new MessageHolder(packet -> {
			tileEntity.cooldown = Math.min(packet.readShort(), 100);
			tileEntity.markDirty();
		}));
	}
	
	public EmptyMessageHolder getValueMessage() {
		return valueMessage;
	}
	
	public MessageHolder getCooldownMessage() {
		return cooldownMessage;
	}
}
