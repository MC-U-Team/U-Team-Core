package info.u_team.u_team_core.intern.network;

import java.util.function.Supplier;

import info.u_team.u_team_core.container.UContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class IntPropertyContainerMessage {
	
	private final int id;
	private final int property;
	private final int value;
	
	public IntPropertyContainerMessage(int id, int property, int value) {
		this.id = id;
		this.property = property;
		this.value = value;
	}
	
	public static void encode(IntPropertyContainerMessage message, PacketBuffer sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.property);
		sendBuffer.writeInt(message.value);
	}
	
	public static IntPropertyContainerMessage decode(PacketBuffer sendBuffer) {
		return new IntPropertyContainerMessage(sendBuffer.readByte(), sendBuffer.readShort(), sendBuffer.readInt());
	}
	
	public static class Handler {
		
		public static void handle(IntPropertyContainerMessage message, Supplier<Context> contextSupplier) {
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
