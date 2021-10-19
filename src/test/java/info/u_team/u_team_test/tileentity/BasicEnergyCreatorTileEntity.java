package info.u_team.u_team_test.tileentity;

import info.u_team.u_team_core.api.block.MenuSyncedBlockEntity;
import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_core.energy.BasicEnergyStorage;
import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import info.u_team.u_team_test.container.BasicEnergyCreatorContainer;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicEnergyCreatorTileEntity extends UBlockEntity implements MenuSyncedBlockEntity {
	
	private final TileEntityUItemStackHandler slots;
	private final BasicEnergyStorage energy;
	
	private final LazyOptional<TileEntityUItemStackHandler> slotsOptional;
	private final LazyOptional<BasicEnergyStorage> energyOptional;
	
	public BasicEnergyCreatorTileEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC_ENERGY_CREATOR.get(), pos, state);
		slots = new TileEntityUItemStackHandler(6, this) {
			
			@Override
			public int getSlotLimit(int slot) {
				return 16;
			}
		};
		energy = new BasicEnergyStorage(1000, 10);
		
		slotsOptional = LazyOptional.of(() -> slots);
		energyOptional = LazyOptional.of(() -> energy);
	}
	
	private boolean action = true;
	
	public static void tick(Level level, BlockPos pos, BlockState state, BasicEnergyCreatorTileEntity blockEntity) {
		if (level.isClientSide()) {
			return;
		}
		if (blockEntity.action) {
			blockEntity.energy.addEnergy(3);
			if (blockEntity.energy.getEnergyStored() == blockEntity.energy.getMaxEnergyStored()) {
				blockEntity.action = !blockEntity.action;
			}
		} else {
			blockEntity.energy.addEnergy(-3);
			if (blockEntity.energy.getEnergyStored() == 0) {
				blockEntity.action = !blockEntity.action;
			}
		}
		blockEntity.setChanged();
	}
	
	@Override
	public void sendInitialDataToClient(FriendlyByteBuf buffer) {
		buffer.writeInt(energy.getEnergyStored());
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleInitialDataFromServer(FriendlyByteBuf buffer) {
		energy.setEnergy(buffer.readInt());
	}
	
	public TileEntityUItemStackHandler getSlots() {
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
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return slotsOptional.cast();
		} else if (capability == CapabilityEnergy.ENERGY) {
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
	
	// Container
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
		return new BasicEnergyCreatorContainer(id, playerInventory, this);
	}
	
	@Override
	public Component getDisplayName() {
		return new TextComponent("Energy creator");
	}
	
}
