package info.u_team.u_team_core.util;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Consumer;

import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.OpenMenuScreenMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class MenuUtil {
	
	public static OptionalInt openMenu(ServerPlayer player, MenuProvider menuProvider, Consumer<FriendlyByteBuf> data, boolean sendClosePacket) {
		if (player.containerMenu != player.inventoryMenu) {
			if (sendClosePacket) {
				player.closeContainer();
			} else {
				player.doCloseContainer();
			}
		}
		player.nextContainerCounter();
		
		final AbstractContainerMenu menu = menuProvider.createMenu(player.containerCounter, player.getInventory(), player);
		if (menu == null) {
			if (player.isSpectator()) {
				player.displayClientMessage(Component.translatable("container.spectatorCantOpen").withStyle(ChatFormatting.RED), true);
			}
			return OptionalInt.empty();
		}
		
		final byte[] extraData = ByteBufUtil.writeCustomDataToByteArray(data); // TODO move to RegistryFriendlyByteBuf or direct StreamCodecs
		
		UCoreNetwork.OPEN_MENU_SCREEN_MESSAGE.sendToPlayer(player, new OpenMenuScreenMessage(menu.containerId, menu.getType(), menuProvider.getDisplayName(), extraData));
		
		player.initMenu(menu);
		player.containerMenu = menu;
		
		Extension.INSTANCES.forEach(extension -> extension.menuOpened(player, menu));
		
		return OptionalInt.of(player.containerCounter);
	}
	
	public interface Extension {
		
		List<Extension> INSTANCES = ServiceUtil.loadAll(Extension.class);
		
		void menuOpened(ServerPlayer player, AbstractContainerMenu menu);
	}
}
