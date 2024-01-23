package info.u_team.u_team_test.test_multiloader.fabric.blockentity;

import info.u_team.u_team_core.api.menu.ItemSlotCreator;
import info.u_team.u_team_core.inventory.BlockEntityUItemStackContainer;
import info.u_team.u_team_core.menu.ItemContainerSlotCreator;
import info.u_team.u_team_test.test_multiloader.blockentity.TestInventoryBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;

public class FabricTestInventoryBlockEntity extends TestInventoryBlockEntity {
	
	private final BlockEntityUItemStackContainer slots;
	private final InventoryStorage slotWrapper;
	
	public FabricTestInventoryBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
		slots = new BlockEntityUItemStackContainer(18, this);
		slotWrapper = InventoryStorage.of(slots, null);
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
	
	public SimpleContainer getSlots() {
		return slots;
	}
	
	// Transaction
	
	public InventoryStorage getSlotWrapper() {
		return slotWrapper;
	}
	
	// Container
	
	public ItemSlotCreator getSlotCreator() {
		return ItemContainerSlotCreator.of(slots);
	}
	
	// Factory
	
	public static class Factory implements TestInventoryBlockEntity.Factory {
		
		@Override
		public TestInventoryBlockEntity create(BlockPos pos, BlockState state) {
			return new FabricTestInventoryBlockEntity(pos, state);
		}
	}
}
