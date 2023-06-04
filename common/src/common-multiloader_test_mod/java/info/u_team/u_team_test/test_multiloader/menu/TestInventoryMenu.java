package info.u_team.u_team_test.test_multiloader.menu;

import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.sync.DataHolder;
import info.u_team.u_team_core.api.sync.MessageHolder;
import info.u_team.u_team_core.api.sync.MessageHolder.EmptyMessageHolder;
import info.u_team.u_team_core.menu.UBlockEntityContainerMenu;
import info.u_team.u_team_test.test_multiloader.blockentity.TestInventoryBlockEntity;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TestInventoryMenu extends UBlockEntityContainerMenu<TestInventoryBlockEntity> {
	
	private EmptyMessageHolder valueMessage;
	private MessageHolder cooldownMessage;
	
	// Client
	public TestInventoryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
		super(TestMultiLoaderMenuTypes.TEST_INVENTORY.get(), containerId, playerInventory, buffer);
	}
	
	// Server
	public TestInventoryMenu(int containerId, Inventory playerInventory, TestInventoryBlockEntity blockEntity) {
		super(TestMultiLoaderMenuTypes.TEST_INVENTORY.get(), containerId, playerInventory, blockEntity);
	}
	
	@Override
	protected void init(NetworkEnvironment environment) {
		addSlots(blockEntity.getSlotCreator(), 2, 9, 8, 41);
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
	
	@Override
	public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
		return null;
	}
}
