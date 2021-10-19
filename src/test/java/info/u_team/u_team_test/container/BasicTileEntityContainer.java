package info.u_team.u_team_test.container;

import info.u_team.u_team_core.api.sync.DataHolder;
import info.u_team.u_team_core.api.sync.MessageHolder;
import info.u_team.u_team_core.api.sync.MessageHolder.EmptyMessageHolder;
import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.init.TestMenus;
import info.u_team.u_team_test.tileentity.BasicTileEntityTileEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fml.LogicalSide;

public class BasicTileEntityContainer extends UBlockEntityContainerMenu<BasicTileEntityTileEntity> {
	
	private EmptyMessageHolder valueMessage;
	private MessageHolder cooldownMessage;
	
	// Client
	public BasicTileEntityContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestMenus.BASIC_BLOCK_ENTITY.get(), id, playerInventory, buffer);
	}
	
	// Server
	public BasicTileEntityContainer(int id, Inventory playerInventory, BasicTileEntityTileEntity tileEntity) {
		super(TestMenus.BASIC_BLOCK_ENTITY.get(), id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(LogicalSide side) {
		addSlots(blockEntity.getSlots(), 2, 9, 8, 41);
		addPlayerInventory(playerInventory, 8, 91);
		
		addDataHolderToClient(DataHolder.createIntHolder(() -> blockEntity.value, value -> blockEntity.value = value));
		addDataHolderToClient(DataHolder.createIntHolder(() -> blockEntity.cooldown, value -> blockEntity.cooldown = value));
		
		valueMessage = addDataHolderToServer(new EmptyMessageHolder(() -> {
			blockEntity.value += 100;
			blockEntity.setChanged();
		}));
		
		cooldownMessage = addDataHolderToServer(new MessageHolder(packet -> {
			blockEntity.cooldown = Math.min(packet.readShort(), 100);
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
