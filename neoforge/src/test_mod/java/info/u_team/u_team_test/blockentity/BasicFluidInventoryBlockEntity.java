package info.u_team.u_team_test.blockentity;

import info.u_team.u_team_core.blockentity.UBlockEntity;
import info.u_team.u_team_core.inventory.BlockEntityUFluidStackHandler;
import info.u_team.u_team_core.inventory.BlockEntityUItemStackHandler;
import info.u_team.u_team_core.inventory.UFluidStackHandler;
import info.u_team.u_team_core.inventory.UItemStackHandler;
import info.u_team.u_team_test.init.TestBlockEntityTypes;
import info.u_team.u_team_test.menu.BasicFluidInventoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class BasicFluidInventoryBlockEntity extends UBlockEntity implements MenuProvider {
	
	protected final UItemStackHandler itemSlots;
	
	protected final UFluidStackHandler fluidTanks;
	
	public BasicFluidInventoryBlockEntity(BlockPos pos, BlockState state) {
		super(TestBlockEntityTypes.BASIC_FLUID_INVENTORY.get(), pos, state);
		
		itemSlots = new BlockEntityUItemStackHandler(4, this);
		
		fluidTanks = new BlockEntityUFluidStackHandler(4, this);
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
	
	// Container
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
		return new BasicFluidInventoryMenu(id, playerInventory, this);
	}
	
	@Override
	public Component getDisplayName() {
		return Component.literal("Fluid Inventory");
	}
}
