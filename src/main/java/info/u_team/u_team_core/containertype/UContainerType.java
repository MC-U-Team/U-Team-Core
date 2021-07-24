package info.u_team.u_team_core.containertype;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.network.IContainerFactory;

import net.minecraft.world.inventory.MenuType.MenuSupplier;

public class UContainerType<T extends AbstractContainerMenu> extends MenuType<T> {
	
	public UContainerType(IContainerFactory<T> forgeFactory) {
		this((MenuSupplier<T>) forgeFactory);
	}
	
	public UContainerType(MenuSupplier<T> factory) {
		super(factory);
	}
}
