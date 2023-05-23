package info.u_team.u_team_core.intern.init.network;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.util.RegistryUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;

public record OpenMenuScreenMessage(int containerId, MenuType<?> type, Component title, FriendlyByteBuf extraData) {
	
	private static final Registry<MenuType<?>> REGISTRY = RegistryUtil.getBuiltInRegistry(Registries.MENU);
	
	public static void encode(OpenMenuScreenMessage message, FriendlyByteBuf buffer) {
		buffer.writeVarInt(message.containerId);
		buffer.writeId(REGISTRY, message.type);
		buffer.writeComponent(message.title);
		buffer.writeByteArray(message.extraData.readByteArray());
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
				try {
					
				} finally {
					message.extraData.release();
				}
			});
		}
	}
}
