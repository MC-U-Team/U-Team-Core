package info.u_team.u_team_core.container;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public abstract class UAbstractContainerMenu extends AbstractContainerMenu {
	
	private ServerPlayer synchronizerPlayer;
	
	protected UAbstractContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
	}
	
	public final void setSynchronizerPlayer(ServerPlayer player) {
		synchronizerPlayer = player;
	}
	
	public void initMenu(ServerPlayer player) {
	}
	
}
