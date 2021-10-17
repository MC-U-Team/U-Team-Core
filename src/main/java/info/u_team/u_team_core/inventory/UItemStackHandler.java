package info.u_team.u_team_core.inventory;

import info.u_team.u_team_core.api.item.ExtendedItemHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class UItemStackHandler extends ItemStackHandler implements ExtendedItemHandler {
	
	public UItemStackHandler(int size) {
		super(size);
	}
	
	@Override
	public void setSize(int size) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public CompoundTag serializeNBT() {
		final var compound = new CompoundTag();
		ContainerHelper.saveAllItems(compound, stacks, false);
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundTag compound) {
		ContainerHelper.loadAllItems(compound, stacks);
		onLoad();
	}
	
	@Override
	public void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
	}
}
