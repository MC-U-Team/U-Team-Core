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

public class SyncedContainerMessage {
	
	private PacketBuffer buffer;
	
	public SyncedContainerMessage(PacketBuffer buffer) {
		this.buffer = buffer;
	}
	
	public static void encode(SyncedContainerMessage message, PacketBuffer sendBuffer) {
		sendBuffer.writeBytes(message.buffer);
	}
	
	public static SyncedContainerMessage decode(PacketBuffer sendBuffer) {
		return new SyncedContainerMessage(new PacketBuffer(sendBuffer.copy()));
	}
	
	public static class Handler {
		
		public static void handle(SyncedContainerMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (context.getDirection().getOriginationSide() == LogicalSide.SERVER) {
					handleClient(message.buffer);
				} else {
					handleServer(message.buffer, context);
				}
			});
			context.setPacketHandled(true);
		}
		
		@OnlyIn(Dist.CLIENT)
		private static void handleClient(PacketBuffer buffer) {
			getSyncedContainer(Minecraft.getInstance().player.openContainer).ifPresent(container -> container.handleFromServer(buffer));
		}
		
		private static void handleServer(PacketBuffer buffer, Context context) {
			getSyncedContainer(context.getSender().openContainer).ifPresent(container -> container.handleFromClient(buffer));
		}
		
		private static final Optional<USyncedContainer> getSyncedContainer(Container container) {
			if (container == null || !(container instanceof USyncedContainer)) {
				return Optional.empty();
			}
			return Optional.of((USyncedContainer) container);
		}
	}
}
