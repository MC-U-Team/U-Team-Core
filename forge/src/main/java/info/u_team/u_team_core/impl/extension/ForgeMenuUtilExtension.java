package info.u_team.u_team_core.impl.extension;

import info.u_team.u_team_core.util.MenuUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

public class ForgeMenuUtilExtension implements MenuUtil.Extension {
	
	@Override
	public void menuOpened(ServerPlayer player, AbstractContainerMenu menu) {
		MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, menu));
	}
	
}
