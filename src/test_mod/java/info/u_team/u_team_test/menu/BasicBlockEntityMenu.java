package info.u_team.u_team_test.menu;

import info.u_team.u_team_core.api.sync.DataHolder;
import info.u_team.u_team_core.api.sync.MessageHolder;
import info.u_team.u_team_core.api.sync.MessageHolder.EmptyMessageHolder;
import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.blockentity.BasicBlockEntityBlockEntity;
import info.u_team.u_team_test.init.TestMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fml.LogicalSide;

public class BasicBlockEntityMenu extends UBlockEntityContainerMenu<BasicBlockEntityBlockEntity> {
	
	private EmptyMessageHolder valueMessage;
	private MessageHolder cooldownMessage;
	
	// Client
	public BasicBlockEntityMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestMenuTypes.BASIC_BLOCK_ENTITY.get(), containerId, playerInventory, buffer);
	}
	
	// Server
	public BasicBlockEntityMenu(int containerId, Inventory playerInventory, BasicBlockEntityBlockEntity tileEntity) {
		super(TestMenuTypes.BASIC_BLOCK_ENTITY.get(), containerId, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		addSlots(blockEntity.getSlots(), 2, 9, 8, 41);
		addPlayerInventory(playerInventory, 8, 91);
		
		addDataHolderToClient(DataHolder.createIntHolder(blockEntity::getValue, blockEntity::setValue));
		addDataHolderToClient(DataHolder.createIntHolder(blockEntity::getCooldown, blockEntity::setCooldown));
		
		valueMessage = addDataHolderToServer(new EmptyMessageHolder(() -> {
			blockEntity.setValue(blockEntity.getValue() + 100);
			blockEntity.setChanged();
		}));
		
		cooldownMessage = addDataHolderToServer(new MessageHolder(packet -> {
			blockEntity.setCooldown(Math.min(packet.readShort(), 100));
			blockEntity.setChanged();
		}));
	}
	
	public EmptyMessageHolder getValueMessage() {
		return valueMessage;
	}
	
	public MessageHolder getCooldownMessage() {
		return cooldownMessage;
	}
}
