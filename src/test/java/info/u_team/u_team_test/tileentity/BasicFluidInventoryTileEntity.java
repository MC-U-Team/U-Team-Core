package info.u_team.u_team_test.tileentity;

import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_core.inventory.TileEntityUFluidStackHandler;
import info.u_team.u_team_core.inventory.TileEntityUItemStackHandler;
import info.u_team.u_team_core.inventory.UFluidStackHandler;
import info.u_team.u_team_core.inventory.UItemStackHandler;
import info.u_team.u_team_test.container.BasicFluidInventoryContainer;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicFluidInventoryTileEntity extends UBlockEntity implements MenuProvider {
	
	protected final UItemStackHandler itemSlots;
	protected final LazyOptional<UItemStackHandler> itemSlotsOptional;
	
	protected final UFluidStackHandler fluidTanks;
	protected final LazyOptional<UFluidStackHandler> fluidTanksOptional;
	
	public BasicFluidInventoryTileEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC_FLUID_INVENTORY.get(), pos, state);
		
		itemSlots = new TileEntityUItemStackHandler(4, this);
		itemSlotsOptional = LazyOptional.of(() -> itemSlots);
		
		fluidTanks = new TileEntityUFluidStackHandler(4, this);
		fluidTanksOptional = LazyOptional.of(() -> fluidTanks);
	}
	
	@Override
	public void saveNBT(CompoundTag compound) {
		super.saveNBT(compound);
		compound.put("items", itemSlots.serializeNBT());
		compound.put("fluids", fluidTanks.serializeNBT());
	}
	
	@Override
	public void loadNBT(CompoundTag compound) {
		super.loadNBT(compound);
		itemSlots.deserializeNBT(compound.getCompound("items"));
		fluidTanks.deserializeNBT(compound.getCompound("fluids"));
	}
	
	public UItemStackHandler getItemSlots() {
		return itemSlots;
	}
	
	public UFluidStackHandler getFluidTanks() {
		return fluidTanks;
	}
	
	// Capability
	
	@Override
	public <X> LazyOptional<X> getCapability(Capability<X> capability, Direction side) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemSlotsOptional.cast();
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return fluidTanksOptional.cast();
		} else {
			return super.getCapability(capability, side);
		}
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		itemSlotsOptional.invalidate();
		fluidTanksOptional.invalidate();
	}
	
	// Container
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
		return new BasicFluidInventoryContainer(id, playerInventory, this);
	}
	
	@Override
	public Component getDisplayName() {
		return new TextComponent("Fluid Inventory");
	}
}
