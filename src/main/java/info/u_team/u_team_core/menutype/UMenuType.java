package info.u_team.u_team_core.menutype;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fmllegacy.network.IContainerFactory;

public class UMenuType<T extends AbstractContainerMenu> extends MenuType<T> {
	
	public UMenuType(ForgeMenuSupplier<T> forgeFactory) {
		super(forgeFactory);
	}
	
	public UMenuType(MenuSupplier<T> factory) {
		super(factory);
	}
	
	public static interface ForgeMenuSupplier<T extends AbstractContainerMenu> extends IContainerFactory<T> {
	}
}
