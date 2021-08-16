package info.u_team.u_team_test.tileentity;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import info.u_team.u_team_core.tileentity.UTileEntity;
import info.u_team.u_team_test.container.BasicTileEntityContainer;
import info.u_team.u_team_test.init.TestTileEntityTypes;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicTileEntityTileEntity extends UTileEntity implements IInitSyncedTileEntity {
	
	private final TileEntityUItemStackHandler slots;
	
	private final LazyOptional<TileEntityUItemStackHandler> slotsOptional;
	
	public int cooldown, value;
	
	public BasicTileEntityTileEntity(BlockPos pos, BlockState state) {
		super(TestTileEntityTypes.BASIC.get(), pos, state);
		slots = new TileEntityUItemStackHandler(18, this);
		slotsOptional = LazyOptional.of(() -> slots);
	}
	
	@Override
	public void sendInitialDataBuffer(FriendlyByteBuf buffer) {
		buffer.writeInt(value);
		buffer.writeInt(cooldown);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataBuffer(FriendlyByteBuf buffer) {
		value = buffer.readInt();
		cooldown = buffer.readInt();
	}
	
	private int timer;
	
	public static void tick(Level level, BlockPos pos, BlockState state, BasicTileEntityTileEntity blockEntity) {
		if (level.isClientSide) {
			return;
		}
		
		if (blockEntity.timer < blockEntity.cooldown) {
			blockEntity.timer++;
			return;
		}
		blockEntity.timer = 0;
		blockEntity.value++;
		blockEntity.setChanged();
	}
	
	@Override
	public void writeNBT(CompoundTag compound) {
		super.writeNBT(compound);
		compound.put("inventory", slots.serializeNBT());
		compound.putInt("value", value);
		compound.putInt("cooldown", cooldown);
	}
	
	@Override
	public void readNBT(CompoundTag compound) {
		super.readNBT(compound);
		value = compound.getInt("value");
		cooldown = compound.getInt("cooldown");
		slots.deserializeNBT(compound.getCompound("inventory"));
	}
	
	public TileEntityUItemStackHandler getSlots() {
		return slots;
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
	
	// Container
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
		return new BasicTileEntityContainer(id, playerInventory, this);
	}
	
	@Override
	public Component getDisplayName() {
		return new TextComponent("Tile Entity");
	}
}
