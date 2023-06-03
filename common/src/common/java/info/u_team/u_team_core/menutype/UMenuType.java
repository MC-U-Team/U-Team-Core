package info.u_team.u_team_core.menutype;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class UMenuType<T extends AbstractContainerMenu> extends MenuType<T> {
	
	public UMenuType(DataMenuSupplier<T> menuSupplier) {
		this((MenuSupplier<T>) menuSupplier);
	}
	
	public UMenuType(MenuSupplier<T> menuSupplier) {
		super(menuSupplier, FeatureFlags.DEFAULT_FLAGS);
	}
	
	public T createWithExtraData(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
		if (constructor instanceof final DataMenuSupplier<T> dataConstructor) {
			return dataConstructor.create(containerId, playerInventory, buffer);
		} else {
			return create(containerId, playerInventory);
		}
	}
	
	@FunctionalInterface
	public static interface DataMenuSupplier<T extends AbstractContainerMenu> extends MenuSupplier<T> {
		
		T create(int containerId, Inventory playerInventory, FriendlyByteBuf buffer);
		
		@Override
		default T create(int containerId, Inventory playerInventory) {
			return create(containerId, playerInventory, null);
		}
	}
}
