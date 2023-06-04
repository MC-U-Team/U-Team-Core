package info.u_team.u_team_core.inventory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;

public class UItemStackContainer extends SimpleContainer {
	
	public UItemStackContainer(int size) {
		super(size);
	}
	
	public CompoundTag serializeNBT() {
		final CompoundTag compound = new CompoundTag();
		ContainerHelper.saveAllItems(compound, items, false);
		return compound;
	}
	
	public void deserializeNBT(CompoundTag compound) {
		ContainerHelper.loadAllItems(compound, items);
		onLoad();
	}
	
	public void onLoad() {
	}
}
