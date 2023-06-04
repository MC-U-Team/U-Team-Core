package info.u_team.u_team_test.test_multiloader.blockentity;

import info.u_team.u_team_core.api.block.MenuSyncedBlockEntity;
import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_core.util.ServiceUtil;
import info.u_team.u_team_test.test_multiloader.init.TestMultiLoaderBlockEntityTypes;
import info.u_team.u_team_test.test_multiloader.menu.TestInventoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TestInventoryBlockEntity extends UBlockEntity implements MenuSyncedBlockEntity {
	
	private int cooldown, timer, value;
	
	protected TestInventoryBlockEntity(BlockPos pos, BlockState state) {
		super(TestMultiLoaderBlockEntityTypes.TEST_INVENTORY.get(), pos, state);
	}
	
	@Override
	public void sendInitialMenuDataToClient(FriendlyByteBuf buffer) {
		buffer.writeInt(value);
		buffer.writeInt(cooldown);
	}
	
	@Override
	public void handleInitialMenuDataFromServer(FriendlyByteBuf buffer) {
		value = buffer.readInt();
		cooldown = buffer.readInt();
	}
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, TestInventoryBlockEntity blockEntity) {
		if (blockEntity.timer < blockEntity.cooldown) {
			blockEntity.timer++;
			return;
		}
		blockEntity.timer = 0;
		blockEntity.value++;
		blockEntity.setChanged();
	}
	
	@Override
	public void saveNBT(CompoundTag compound) {
		compound.putInt("value", value);
		compound.putInt("cooldown", cooldown);
	}
	
	@Override
	public void loadNBT(CompoundTag compound) {
		value = compound.getInt("value");
		cooldown = compound.getInt("cooldown");
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	// Container
	
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new TestInventoryMenu(containerId, playerInventory, this);
	}
	
	@Override
	public Component getDisplayName() {
		return Component.literal("Test");
	}
	
	public abstract ItemSlotCreator getSlotCreator();
	
	// Factory
	
	public interface Factory {
		
		Factory INSTANCE = ServiceUtil.loadOne(Factory.class);
		
		TestInventoryBlockEntity create(BlockPos pos, BlockState state);
	}
}
