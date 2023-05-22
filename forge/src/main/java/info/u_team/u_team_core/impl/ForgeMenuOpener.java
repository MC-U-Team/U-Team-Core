package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.menu.MenuOpener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.network.NetworkHooks;

public class ForgeMenuOpener extends MenuOpener {
	
	@Override
	protected void open(ServerPlayer player, MenuProvider menuProvider, Consumer<FriendlyByteBuf> data) {
		NetworkHooks.openScreen(player, menuProvider, data);
	}
}
