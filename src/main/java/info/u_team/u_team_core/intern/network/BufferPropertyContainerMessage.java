package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.UContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class BufferPropertyContainerMessage {
	
	private final int id;
	private final int property;
	private final PacketBuffer buffer;
	
	public BufferPropertyContainerMessage(int id, int property, PacketBuffer buffer) {
		this.id = id;
		this.property = property;
		this.buffer = buffer;
	}
	
	public static void encode(BufferPropertyContainerMessage message, PacketBuffer sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.property);
		sendBuffer.writeBytes(message.buffer);
	}
	
	public static BufferPropertyContainerMessage decode(PacketBuffer sendBuffer) {
		return new BufferPropertyContainerMessage(sendBuffer.readByte(), sendBuffer.readShort(), new PacketBuffer(sendBuffer.readBytes(Unpooled.buffer())));
	}
	
	public int getProperty() {
		return property;
	}
	
	public PacketBuffer getBuffer() {
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
			getUContainer(context.getSender().openContainer, message.id).ifPresent(container -> container.updateValue(message, LogicalSide.SERVER));
		}
		
		@OnlyIn(Dist.CLIENT)
		private static void handleClient(BufferPropertyContainerMessage message) {
			getUContainer(Minecraft.getInstance().player.openContainer, message.id).ifPresent(container -> container.updateValue(message, LogicalSide.CLIENT));
		}
		
		private static final Optional<UContainer> getUContainer(Container container, int id) {
			if (container instanceof UContainer && container.windowId == id) {
				return Optional.of((UContainer) container);
			}
			return Optional.empty();
		}
	}
}
