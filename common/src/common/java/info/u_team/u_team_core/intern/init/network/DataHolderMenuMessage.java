package info.u_team.u_team_core.intern.init.network;

import java.util.Optional;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.menu.UContainerMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;

public record DataHolderMenuMessage(int containerId, int index, FriendlyByteBuf dataHolderBuffer) {
	
	public static void encode(DataHolderMenuMessage message, FriendlyByteBuf buffer) {
		buffer.writeByte(message.containerId);
		buffer.writeShort(message.index);
		buffer.writeVarInt(message.dataHolderBuffer.readableBytes());
		buffer.writeBytes(message.dataHolderBuffer);
		message.dataHolderBuffer.release();
	}
	
	public static DataHolderMenuMessage decode(FriendlyByteBuf buffer) {
		final int containerId = buffer.readByte();
		final int index = buffer.readShort();
		final FriendlyByteBuf dataHolderBuffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(buffer.readByteArray()));
		return new DataHolderMenuMessage(containerId, index, dataHolderBuffer);
	}
	
	public static class Handler {
		
		public static void handle(DataHolderMenuMessage message, NetworkContext context) {
			context.executeOnMainThread(() -> {
				updateDataHolder(context.getPlayer().containerMenu, context.getEnvironment(), message);
				message.dataHolderBuffer.release();
			});
		}
		
		private static void updateDataHolder(AbstractContainerMenu menuToTest, NetworkEnvironment environment, DataHolderMenuMessage message) {
			testContainerMenu(menuToTest, message.containerId).ifPresent(menu -> menu.setDataHolder(environment, message.index, message.dataHolderBuffer));
		}
		
		private static Optional<UContainerMenu> testContainerMenu(AbstractContainerMenu menu, int containerId) {
			if (menu instanceof final UContainerMenu uContainer && menu.containerId == containerId) {
				return Optional.of(uContainer);
			}
			return Optional.empty();
		}
	}
}
