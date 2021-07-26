package info.u_team.u_team_test.container;

import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import info.u_team.u_team_core.api.sync.MessageHolder;
import info.u_team.u_team_core.api.sync.MessageHolder.EmptyMessageHolder;
import info.u_team.u_team_core.container.UTileEntityContainer;
import info.u_team.u_team_test.init.TestContainers;
import info.u_team.u_team_test.tileentity.BasicTileEntityTileEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class BasicTileEntityContainer extends UTileEntityContainer<BasicTileEntityTileEntity> {

	private EmptyMessageHolder valueMessage;
	private MessageHolder cooldownMessage;

	// Client
	public BasicTileEntityContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestContainers.BASIC.get(), id, playerInventory, buffer);
	}

	// Server
	public BasicTileEntityContainer(int id, Inventory playerInventory, BasicTileEntityTileEntity tileEntity) {
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
			tileEntity.setChanged();
		}));

		cooldownMessage = addClientToServerTracker(new MessageHolder(packet -> {
			tileEntity.cooldown = Math.min(packet.readShort(), 100);
			tileEntity.setChanged();
		}));
	}

	public EmptyMessageHolder getValueMessage() {
		return valueMessage;
	}

	public MessageHolder getCooldownMessage() {
		return cooldownMessage;
	}
}
