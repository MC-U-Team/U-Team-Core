package info.u_team.u_team_core.menu;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

/**
 * Enhanced version of {@link AbstractContainerMenu} with the benefit that the player that opened the container is known
 * and a method is called when the container is opened on the logical server.
 * 
 * @author HyCraftHD
 */
public abstract class UAbstractContainerMenu extends AbstractContainerMenu {
	
	private ServerPlayer synchronizerPlayer;
	
	/**
	 * Creates an container menu. Must be implemented by a sub class to be used.
	 * 
	 * @param menuType Menu type
	 * @param containerId Container id
	 */
	protected UAbstractContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
	}
	
	/**
	 * Do not call your self. Will be called from an asm hook inside {@link ServerPlayer#initMenu(AbstractContainerMenu)}.
	 * This method sets player that has the container opened.
	 * 
	 * @param player Server player that opened the container
	 */
	public final void setSynchronizerPlayer(ServerPlayer player) {
		synchronizerPlayer = player;
	}
	
	/**
	 * Returns the player that opened the container and should be used for synchronizing purposes.
	 * 
	 * @return Server player that opened the container
	 */
	public final ServerPlayer getSynchronizerPlayer() {
		return synchronizerPlayer;
	}
	
	/**
	 * Will be called immediately after the {@link #setSynchronizerPlayer(ServerPlayer)}.
	 * 
	 * @param player Server player that openend the container
	 */
	public void initMenu(ServerPlayer player) {
	}
	
}
