package info.u_team.u_team_test.blockentity;

import info.u_team.u_team_core.api.block.MenuSyncedBlockEntity;
import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.inventory.BlockEntityUItemStackHandler;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import info.u_team.u_team_test.menu.BasicEnergyCreatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class BasicEnergyCreatorBlockEntity extends UBlockEntity implements MenuSyncedBlockEntity {
	
	private final BlockEntityUItemStackHandler slots;
	private final BasicEnergyStorage energy;
	
	private final LazyOptional<BlockEntityUItemStackHandler> slotsOptional;
	private final LazyOptional<BasicEnergyStorage> energyOptional;
	
	private boolean action = true;
	
	public BasicEnergyCreatorBlockEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC_ENERGY_CREATOR.get(), pos, state);
		slots = new BlockEntityUItemStackHandler(6, this) {
			
			@Override
			public int getSlotLimit(int slot) {
				return 16;
			}
		};
		energy = new BasicEnergyStorage(10_000_000, 10);
		
		slotsOptional = LazyOptional.of(() -> slots);
		energyOptional = LazyOptional.of(() -> energy);
	}
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, BasicEnergyCreatorBlockEntity blockEntity) {
		if (blockEntity.action) {
			blockEntity.energy.addEnergy(3 * 10000);
			if (blockEntity.energy.getEnergyStored() == blockEntity.energy.getMaxEnergyStored()) {
				blockEntity.action = !blockEntity.action;
			}
		} else {
			blockEntity.energy.addEnergy(-3 * 10000);
			if (blockEntity.energy.getEnergyStored() == 0) {
				blockEntity.action = !blockEntity.action;
			}
		}
		blockEntity.setChanged();
	}
	
	@Override
	public void sendInitialMenuDataToClient(FriendlyByteBuf buffer) {
		buffer.writeInt(energy.getEnergyStored());
	}
	
	@Override
	public void handleInitialMenuDataFromServer(FriendlyByteBuf buffer) {
		energy.setEnergy(buffer.readInt());
	}
	
	public BlockEntityUItemStackHandler getSlots() {
		return slots;
	}
	
	public BasicEnergyStorage getEnergy() {
		return energy;
	}
	
	@Override
	public void saveNBT(CompoundTag compound) {
		super.saveNBT(compound);
		compound.put("inventory", slots.serializeNBT());
		compound.put("energy", energy.serializeNBT());
	}
	
	@Override
	public void loadNBT(CompoundTag compound) {
		super.loadNBT(compound);
		slots.deserializeNBT(compound.getCompound("inventory"));
		energy.deserializeNBT(compound.getCompound("energy"));
	}
	
	// Capability
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
		if (capability == ForgeCapabilities.ITEM_HANDLER) {
			return slotsOptional.cast();
		} else if (capability == ForgeCapabilities.ENERGY) {
			return energyOptional.cast();
		} else {
			return super.getCapability(capability, side);
		}
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		slotsOptional.invalidate();
		energyOptional.invalidate();
	}
	
	@Override
	public void onChunkUnloaded() {
		super.onChunkUnloaded();
		slotsOptional.invalidate();
		energyOptional.invalidate();
	}
	
	// Container
	
	@Override
	public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
		return new BasicEnergyCreatorMenu(containerId, playerInventory, this);
	}
	
	@Override
	public Component getDisplayName() {
		return Component.literal("Energy creator");
	}
	
}
