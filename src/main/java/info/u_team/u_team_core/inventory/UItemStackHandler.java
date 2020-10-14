package info.u_team.u_team_core.inventory;

import info.u_team.u_team_core.api.item.IExtendedItemHandler;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

public class UItemStackHandler extends ItemStackHandler implements IExtendedItemHandler {
	
	public UItemStackHandler(int size) {
		super(size);
	}
	
	@Override
	public void setSize(int size) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		final CompoundNBT compound = new CompoundNBT();
		ItemStackHelper.saveAllItems(compound, stacks, false);
		return compound;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT compound) {
		ItemStackHelper.loadAllItems(compound, stacks);
		onLoad();
	}
	
	@Override
	public void onContentsChanged(int slot) {
		super.onContentsChanged(slot);
	}
}