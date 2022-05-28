package info.u_team.u_team_core.intern.asm;

import info.u_team.u_team_core.menu.UAbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Class used for an asm hook (ucontainermenu-init-menu.js) for the {@link UAbstractContainerMenu}. <br>
 * <strong>Do not change package, class or method names without changing them in the asm core mod!</strong>
 *
 * @author HyCraftHD
 */
public class ASMUContainerMenuHook {
	
	/**
	 * Called from asm core mod from {@link ServerPlayer#initMenu(AbstractContainerMenu)} method.
	 */
	public static void hook(AbstractContainerMenu menu, ServerPlayer player) {
		if (menu instanceof final UAbstractContainerMenu uContainerMenu) {
			uContainerMenu.setSynchronizerPlayer(player);
			uContainerMenu.initMenu(player);
		}
	}
	
}
