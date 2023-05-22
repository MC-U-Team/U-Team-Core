package info.u_team.u_team_core.impl;

import java.util.function.Consumer;

import info.u_team.u_team_core.api.menu.MenuOpener;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class FabricMenuOpener extends MenuOpener {
	
	@Override
	protected void open(ServerPlayer player, MenuProvider menuProvider, Consumer<FriendlyByteBuf> data) {
		player.openMenu(new ExtendedScreenHandlerFactory() {
			
			@Override
			public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
				return menuProvider.createMenu(containerId, playerInventory, player);
			}
			
			@Override
			public Component getDisplayName() {
				return menuProvider.getDisplayName();
			}
			
			@Override
			public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf byteBuf) {
				data.accept(byteBuf);
			}
			
			@Override
			public boolean shouldCloseCurrentScreen() {
				return menuProvider.shouldCloseCurrentScreen();
			}
		});
	}
}
