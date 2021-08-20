package info.u_team.u_team_core.intern.asm;

import info.u_team.u_team_core.container.UContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ASMUContainerMenuHook {
	
	/**
	 * Called from asm core mod (ucontainermenu-init-menu.js)
	 */
	public static void hook(AbstractContainerMenu menu, ServerPlayer player) {
		if (menu instanceof UContainerMenu uContainerMenu) {
			uContainerMenu.initMenu(player);
		}
	}
	
}
