package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.USyncedContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSyncedContainer {
	
	private PacketBuffer buffer;
	
	public MessageSyncedContainer(PacketBuffer buffer) {
		this.buffer = buffer;
	}
	
	public static void encode(MessageSyncedContainer message, PacketBuffer sendBuffer) {
		sendBuffer.writeBytes(message.buffer);
	}
	
	public static MessageSyncedContainer decode(PacketBuffer sendBuffer) {
		return new MessageSyncedContainer(new PacketBuffer(sendBuffer.copy()));
	}
	
	public static class Handler {
		
		public static void handle(MessageSyncedContainer message, Supplier<Context> ctxSupplier) {
			Context ctx = ctxSupplier.get();
			ctx.enqueueWork(() -> {
				if (ctx.getDirection().getOriginationSide() == LogicalSide.SERVER) {
					handleClient(message.buffer, ctx);
				} else {
					handleServer(message.buffer, ctx);
				}
			});
			ctx.setPacketHandled(true);
		}
		
		@OnlyIn(Dist.CLIENT)
		private static void handleClient(PacketBuffer buffer, Context ctx) {
			getSyncedContainer(Minecraft.getInstance().player.openContainer).ifPresent(container -> container.handleFromServer(buffer));
		}
		
		private static void handleServer(PacketBuffer buffer, Context ctx) {
			getSyncedContainer(ctx.getSender().openContainer).ifPresent(container -> container.handleFromServer(buffer));
		}
		
		private static final Optional<USyncedContainer> getSyncedContainer(Container container) {
			if (container == null || !(container instanceof USyncedContainer)) {
				return Optional.empty();
			}
			return Optional.of((USyncedContainer) container);
		}
	}
}
