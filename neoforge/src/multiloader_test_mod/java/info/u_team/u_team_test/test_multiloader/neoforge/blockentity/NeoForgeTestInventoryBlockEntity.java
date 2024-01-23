package info.u_team.u_team_test.test_multiloader.neoforge.blockentity;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import info.u_team.u_team_core.inventory.BlockEntityUItemStackHandler;
import info.u_team.u_team_core.menu.ItemHandlerSlotCreator;
import info.u_team.u_team_test.test_multiloader.blockentity.TestInventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class NeoForgeTestInventoryBlockEntity extends TestInventoryBlockEntity {
	
	private final BlockEntityUItemStackHandler slots;
	
	public NeoForgeTestInventoryBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
		slots = new BlockEntityUItemStackHandler(18, this);
	}
	
	@Override
	public void saveNBT(CompoundTag compound) {
		super.saveNBT(compound);
		compound.put("inventory", slots.serializeNBT());
	}
	
	@Override
	public void loadNBT(CompoundTag compound) {
		super.loadNBT(compound);
		slots.deserializeNBT(compound.getCompound("inventory"));
	}
	
	public BlockEntityUItemStackHandler getSlots() {
		return slots;
	}
	
	// Container
	
	public ItemSlotCreator getSlotCreator() {
		return ItemHandlerSlotCreator.of(slots);
	}
	
	// Factory
	
	public static class Factory implements TestInventoryBlockEntity.Factory {
		
		@Override
		public TestInventoryBlockEntity create(BlockPos pos, BlockState state) {
			return new NeoForgeTestInventoryBlockEntity(pos, state);
		}
	}
}
