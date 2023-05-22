package info.u_team.u_team_core.api.menu;

import java.util.function.Consumer;

import info.u_team.u_team_core.util.ServiceUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

public abstract class MenuOpener {
	
	private static MenuOpener INSTANCE = ServiceUtil.loadOne(MenuOpener.class);
	
	public static void openMenu(ServerPlayer player, MenuProvider menuProvider, Consumer<FriendlyByteBuf> data) {
		INSTANCE.open(player, menuProvider, data);
	}
	
	protected abstract void open(ServerPlayer player, MenuProvider menuProvider, Consumer<FriendlyByteBuf> data);
}
