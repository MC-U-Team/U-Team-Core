package info.u_team.u_team_core.intern.network;

import java.util.Set;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_core.intern.network.OpenMenuScreenPayload.OpenMenuScreenMessage;
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

public class OpenMenuScreenPayload implements NetworkPayload<OpenMenuScreenMessage> {
	
	public static record OpenMenuScreenMessage(int containerId, MenuType<?> type, Component title, FriendlyByteBuf extraData) {
	}
	
	private final Registry<MenuType<?>> registry = RegistryUtil.getBuiltInRegistry(Registries.MENU);
	
	@Override
	public Set<NetworkEnvironment> getHandlerEnvironment() {
		return Set.of(NetworkEnvironment.CLIENT);
	}
	
	@Override
	public void write(OpenMenuScreenMessage message, FriendlyByteBuf buffer) {
		buffer.writeVarInt(message.containerId);
		buffer.writeId(registry, message.type);
		buffer.writeComponent(message.title);
		buffer.writeVarInt(message.extraData.readableBytes());
		buffer.writeBytes(message.extraData);
		message.extraData.release();
	}
	
	@Override
	public OpenMenuScreenMessage read(FriendlyByteBuf buffer) {
		final int containerId = buffer.readVarInt();
		final MenuType<?> type = buffer.readById(registry);
		final Component title = buffer.readComponentTrusted();
		final FriendlyByteBuf extraData = new FriendlyByteBuf(Unpooled.wrappedBuffer(buffer.readByteArray()));
		return new OpenMenuScreenMessage(containerId, type, title, extraData);
	}
	
	@Override
	public void handle(OpenMenuScreenMessage message, NetworkContext context) {
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
					MenuScreens.LOGGER.warn("Failed to create screen for menu type: {}", registry.getKey(type));
					return;
				}
				
				final AbstractContainerMenu menu;
				if (type instanceof final UMenuType<?> uType) {
					menu = uType.createWithExtraData(containerId, playerInventory, extraData);
				} else {
					menu = type.create(containerId, playerInventory);
				}
				
				final Screen screen = constructor.create(menu, playerInventory, title);
				if (!(screen instanceof final MenuAccess<?> menuAccess)) {
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
