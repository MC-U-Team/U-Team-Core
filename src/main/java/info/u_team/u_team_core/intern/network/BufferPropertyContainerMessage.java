package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.UContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class BufferPropertyContainerMessage {
	
	private final int id;
	private final int property;
	private final FriendlyByteBuf buffer;
	
	public BufferPropertyContainerMessage(int id, int property, FriendlyByteBuf buffer) {
		this.id = id;
		this.property = property;
		this.buffer = buffer;
	}
	
	public static void encode(BufferPropertyContainerMessage message, FriendlyByteBuf sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.property);
		sendBuffer.writeBytes(message.buffer);
	}
	
	public static BufferPropertyContainerMessage decode(FriendlyByteBuf sendBuffer) {
		final int id = sendBuffer.readByte();
		final int property = sendBuffer.readShort();
		
		final byte[] bytes = new byte[sendBuffer.readableBytes()]; // Is there a better way to read all bytes??
		sendBuffer.getBytes(sendBuffer.readerIndex(), bytes);
		final BufferPropertyContainerMessage buffer = new BufferPropertyContainerMessage(id, property, new FriendlyByteBuf(Unpooled.wrappedBuffer(bytes)));
		
		return buffer;
	}
	
	public int getProperty() {
		return property;
	}
	
	public FriendlyByteBuf getBuffer() {
		return buffer;
	}
	
	public static class Handler {
		
		public static void handle(BufferPropertyContainerMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				
				if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
					handleClient(message);
				} else {
					handleServer(message, context);
				}
			});
			context.setPacketHandled(true);
		}
		
		private static void handleServer(BufferPropertyContainerMessage message, Context context) {
			getUContainer(context.getSender().containerMenu, message.id).ifPresent(container -> container.updateValue(message, LogicalSide.SERVER));
		}
		
		@OnlyIn(Dist.CLIENT)
		private static void handleClient(BufferPropertyContainerMessage message) {
			getUContainer(Minecraft.getInstance().player.containerMenu, message.id).ifPresent(container -> container.updateValue(message, LogicalSide.CLIENT));
		}
		
		private static final Optional<UContainer> getUContainer(AbstractContainerMenu container, int id) {
			if (container instanceof UContainer && container.containerId == id) {
				return Optional.of((UContainer) container);
			}
			return Optional.empty();
		}
	}
}
