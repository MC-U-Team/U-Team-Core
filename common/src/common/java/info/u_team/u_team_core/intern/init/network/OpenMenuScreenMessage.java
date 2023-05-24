package info.u_team.u_team_core.intern.init.network;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.menutype.UMenuType;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.RegistryUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public record OpenMenuScreenMessage(int containerId, MenuType<?> type, Component title, FriendlyByteBuf extraData) {
	
	private static final Registry<MenuType<?>> REGISTRY = RegistryUtil.getBuiltInRegistry(Registries.MENU);
	
	public static void encode(OpenMenuScreenMessage message, FriendlyByteBuf buffer) {
		buffer.writeVarInt(message.containerId);
		buffer.writeId(REGISTRY, message.type);
		buffer.writeComponent(message.title);
		buffer.writeVarInt(message.extraData.readableBytes());
		buffer.writeBytes(message.extraData);
		message.extraData.release();
	}
	
	public static OpenMenuScreenMessage decode(FriendlyByteBuf buffer) {
		final int containerId = buffer.readVarInt();
		final MenuType<?> type = buffer.readById(REGISTRY);
		final Component title = buffer.readComponent();
		final FriendlyByteBuf extraData = new FriendlyByteBuf(Unpooled.wrappedBuffer(buffer.readByteArray()));
		return new OpenMenuScreenMessage(containerId, type, title, extraData);
	}
	
	public static class Handler {
		
		public static void handle(OpenMenuScreenMessage message, NetworkContext context) {
			context.executeOnMainThread(() -> {
				final Minecraft minecraft = Minecraft.getInstance();
				final Inventory playerInventory = minecraft.player.getInventory();
				final int containerId = message.containerId;
				final MenuType<?> type = message.type;
				final Component title = message.title;
				final FriendlyByteBuf extraData = message.extraData;
				
				try {
					if (message.type == null) {
						MenuScreens.LOGGER.warn("Trying to open invalid screen with name: {}", title.getString());
						return;
					}
					final ScreenConstructor<AbstractContainerMenu, ?> constructor = CastUtil.uncheckedCast(MenuScreens.getConstructor(type));
					if (constructor == null) {
						MenuScreens.LOGGER.warn("Failed to create screen for menu type: {}", REGISTRY.getKey(type));
						return;
					}
					
					final AbstractContainerMenu menu;
					if (type instanceof UMenuType<?> uType) {
						menu = uType.createWithExtraData(containerId, playerInventory, extraData);
					} else {
						menu = type.create(containerId, playerInventory);
					}
					
					final Screen screen = constructor.create(menu, playerInventory, title);
					if (!(screen instanceof MenuAccess<?> menuAccess)) {
						MenuScreens.LOGGER.warn("Trying to open invalid screen that does not implement MenuAccess with name: {}", title.getString());
						return;
					}
					
					minecraft.player.containerMenu = menuAccess.getMenu();
					minecraft.setScreen(screen);
				} finally {
					extraData.release();
				}
			});
		}
	}
}
