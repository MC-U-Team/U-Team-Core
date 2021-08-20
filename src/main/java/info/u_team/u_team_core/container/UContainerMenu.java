package info.u_team.u_team_core.container;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public abstract class UContainerMenu extends AbstractContainerMenu {
	
	protected UContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
	}

	public void initMenu(ServerPlayer player) {
		System.out.println("player" + player);
		System.out.println("_CALLED FROM ASM!!");
	}

}
