package info.u_team.u_team_test.tileentity;

import info.u_team.u_team_core.inventory.*;
import info.u_team.u_team_core.tileentity.UTickableTileEntity;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class BasicFluidInventoryTileEntity extends UTickableTileEntity {
	
	protected final UItemStackHandler itemSlots;
	protected final LazyOptional<UItemStackHandler> itemSlotsOptional;
	
	protected final UFluidStackHandler fluidTanks;
	protected final LazyOptional<UFluidStackHandler> fluidTanksOptional;
	
	public BasicFluidInventoryTileEntity() {
		super(TestTileEntityTypes.BASIC_FLUID_INVENTORY);
		
		itemSlots = new TileEntityUItemStackHandler(4, this);
		itemSlotsOptional = LazyOptional.of(() -> itemSlots);
		
		fluidTanks = new TileEntityUFluidStackHandler(4, this);
		fluidTanksOptional = LazyOptional.of(() -> fluidTanks);
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		super.writeNBT(compound);
		compound.put("items", itemSlots.serializeNBT());
		compound.put("fluids", fluidTanks.serializeNBT());
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		super.readNBT(compound);
		itemSlots.deserializeNBT(compound.getCompound("items"));
		fluidTanks.deserializeNBT(compound.getCompound("fluids"));
	}
	
	@Override
	public void remove() {
		super.remove();
		itemSlotsOptional.invalidate();
		fluidTanksOptional.invalidate();
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
	
}
