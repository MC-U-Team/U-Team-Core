package info.u_team.u_team_test.blockentity;

import info.u_team.u_team_core.api.block.MenuSyncedBlockEntity;
import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import info.u_team.u_team_test.menu.BasicBlockEntityMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicBlockEntityBlockEntity extends UBlockEntity implements MenuSyncedBlockEntity {
	
	private final TileEntityUItemStackHandler slots;
	private final LazyOptional<TileEntityUItemStackHandler> slotsOptional;
	
	private int cooldown, timer, value;
	
	public BasicBlockEntityBlockEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC.get(), pos, state);
		slots = new TileEntityUItemStackHandler(18, this);
		slotsOptional = LazyOptional.of(() -> slots);
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
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, BasicBlockEntityBlockEntity blockEntity) {
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
		compound.put("inventory", slots.serializeNBT());
	}
	
	@Override
	public void loadNBT(CompoundTag compound) {
		value = compound.getInt("value");
		cooldown = compound.getInt("cooldown");
		slots.deserializeNBT(compound.getCompound("inventory"));
	}
	
	public TileEntityUItemStackHandler getSlots() {
		return slots;
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
	
	// Capability
	
	@Override
	public <X> LazyOptional<X> getCapability(Capability<X> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return slotsOptional.cast();
		} else {
			return super.getCapability(capability, side);
		}
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		slotsOptional.invalidate();
	}
	
	@Override
	public void onChunkUnloaded() {
		super.onChunkUnloaded();
		slotsOptional.invalidate();
	}
	
	// Container
	
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new BasicBlockEntityMenu(containerId, playerInventory, this);
	}
	
	@Override
	public Component getDisplayName() {
		return new TextComponent("Tile Entity");
	}
}
