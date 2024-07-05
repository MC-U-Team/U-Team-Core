package info.u_team.u_team_test.test_multiloader.forge.blockentity;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import info.u_team.u_team_core.inventory.BlockEntityUItemStackHandler;
import info.u_team.u_team_core.menu.ItemHandlerSlotCreator;
import info.u_team.u_team_test.test_multiloader.blockentity.TestInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class ForgeTestInventoryBlockEntity extends TestInventoryBlockEntity {
	
	private final BlockEntityUItemStackHandler slots;
	private final LazyOptional<BlockEntityUItemStackHandler> slotsOptional;
	
	public ForgeTestInventoryBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
		slots = new BlockEntityUItemStackHandler(18, this);
		slotsOptional = LazyOptional.of(() -> slots);
	}
	
	@Override
	public void saveNBT(CompoundTag compound, HolderLookup.Provider lookup) {
		super.saveNBT(compound, lookup);
		compound.put("inventory", slots.serializeNBT(lookup));
	}
	
	@Override
	public void loadNBT(CompoundTag compound, HolderLookup.Provider lookup) {
		super.loadNBT(compound, lookup);
		slots.deserializeNBT(lookup, compound.getCompound("inventory"));
	}
	
	public BlockEntityUItemStackHandler getSlots() {
		return slots;
	}
	
	// Capability
	
	@Override
	public <X> LazyOptional<X> getCapability(Capability<X> capability, Direction side) {
		if (capability == ForgeCapabilities.ITEM_HANDLER) {
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
	public ItemSlotCreator getSlotCreator() {
		return ItemHandlerSlotCreator.of(slots);
	}
	
	// Factory
	
	public static class Factory implements TestInventoryBlockEntity.Factory {
		
		@Override
		public TestInventoryBlockEntity create(BlockPos pos, BlockState state) {
			return new ForgeTestInventoryBlockEntity(pos, state);
		}
	}
}
