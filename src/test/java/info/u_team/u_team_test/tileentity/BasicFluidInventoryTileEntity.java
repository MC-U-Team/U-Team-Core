package info.u_team.u_team_test.tileentity;

import info.u_team.u_team_core.inventory.*;
import info.u_team.u_team_core.tileentity.UTickableTileEntity;
import info.u_team.u_team_test.init.TestTileEntityTypes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.LazyOptional;

public class BasicFluidInventoryTileEntity extends UTickableTileEntity {
	
	protected final UItemStackHandler itemSlots;
	protected final LazyOptional<UItemStackHandler> itemSlotsOptional;
	
	protected final UFluidStackHandler fluidTanks;
	protected final LazyOptional<UFluidStackHandler> fluidTanksOptional;
	
	public BasicFluidInventoryTileEntity() {
		super(TestTileEntityTypes.BASIC_FLUID_INVENTORY);
		
		itemSlots = new TileEntityUItemStackHandler(4, this);
		itemSlotsOptional = LazyOptional.of(() -> itemSlots);
		
		fluidTanks = new UFluidStackHandler(4); // TODO tile entity u fluid stack handler
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
	
}
