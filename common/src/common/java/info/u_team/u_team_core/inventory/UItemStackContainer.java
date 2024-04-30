package info.u_team.u_team_core.inventory;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;

public class UItemStackContainer extends SimpleContainer {
	
	public UItemStackContainer(int size) {
		super(size);
	}
	
	public CompoundTag serializeNBT(HolderLookup.Provider registry) {
		final CompoundTag compound = new CompoundTag();
		ContainerHelper.saveAllItems(compound, items, false, registry);
		return compound;
	}
	
	public void deserializeNBT(CompoundTag compound, HolderLookup.Provider registry) {
		ContainerHelper.loadAllItems(compound, items, registry);
		onLoad();
	}
	
	public void onLoad() {
	}
}
