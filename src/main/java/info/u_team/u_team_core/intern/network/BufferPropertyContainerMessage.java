package info.u_team.u_team_core.intern.network;

import java.util.function.Supplier;

import info.u_team.u_team_core.container.UContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
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
	
	public static class Handler {
		
		public static void handle(BufferPropertyContainerMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				final PlayerEntity player = Minecraft.getInstance().player;
				final Container container = player.openContainer;
				
				if (container instanceof UContainer && container.windowId == message.id) {
					((UContainer) container).updateTrackRealInts(message.property, message.value);
				}
			});
			context.setPacketHandled(true);
		}
	}
}
