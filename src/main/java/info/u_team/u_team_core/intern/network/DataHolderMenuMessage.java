package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.menu.UContainerMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class DataHolderMenuMessage {
	
	private final int containerId;
	private final int index;
	private final FriendlyByteBuf dataHolderBuffer;
	
	public DataHolderMenuMessage(int id, int property, FriendlyByteBuf dataHolderBuffer) {
		this.containerId = id;
		this.index = property;
		this.dataHolderBuffer = dataHolderBuffer;
	}
	
	public static void encode(DataHolderMenuMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.containerId);
		byteBuf.writeShort(message.index);
		byteBuf.writeBytes(message.dataHolderBuffer);
	}
	
	public static DataHolderMenuMessage decode(FriendlyByteBuf byteBuf) {
		final int containerId = byteBuf.readByte();
		final int index = byteBuf.readShort();
		
		final var bytes = new byte[byteBuf.readableBytes()]; // Is there a better way to read all bytes??
		byteBuf.readBytes(bytes);
		
		return new DataHolderMenuMessage(containerId, index, new FriendlyByteBuf(Unpooled.wrappedBuffer(bytes)));
	}
	
	public static class Handler {
		
		public static void handle(DataHolderMenuMessage message, Supplier<Context> contextSupplier) {
			final var context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
					handleClient(message);
				} else {
					handleServer(message, context);
				}
			});
			context.setPacketHandled(true);
		}
		
		@OnlyIn(Dist.CLIENT)
		private static void handleClient(DataHolderMenuMessage message) {
			updateDataHolder(Minecraft.getInstance().player.containerMenu, LogicalSide.CLIENT, message);
		}
		
		private static void handleServer(DataHolderMenuMessage message, Context context) {
			updateDataHolder(context.getSender().containerMenu, LogicalSide.CLIENT, message);
		}
		
		private static void updateDataHolder(AbstractContainerMenu menuToTest, LogicalSide side, DataHolderMenuMessage message) {
			testContainerMenu(menuToTest, message.containerId).ifPresent(menu -> menu.setDataHolder(side, message.index, message.dataHolderBuffer));
		}
		
		private static Optional<UContainerMenu> testContainerMenu(AbstractContainerMenu menu, int containerId) {
			if (menu instanceof UContainerMenu uContainer && menu.containerId == containerId) {
				return Optional.of(uContainer);
			}
			return Optional.empty();
		}
	}
}
