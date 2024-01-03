package info.u_team.u_team_core.impl.extension;

import info.u_team.u_team_core.util.MenuUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

public class NeoForgeMenuUtilExtension implements MenuUtil.Extension {
	
	@Override
	public void menuOpened(ServerPlayer player, AbstractContainerMenu menu) {
		NeoForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, menu));
	}
	
}
