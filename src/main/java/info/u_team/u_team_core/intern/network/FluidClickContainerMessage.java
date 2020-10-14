package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.FluidContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class FluidClickContainerMessage {
	
	private final int id;
	private final int slot;
	private final boolean shift;
	private final ItemStack stack;
	
	public FluidClickContainerMessage(int id, int slot, boolean shift, ItemStack stack) {
		this.id = id;
		this.slot = slot;
		this.shift = shift;
		this.stack = stack;
	}
	
	public static void encode(FluidClickContainerMessage message, PacketBuffer sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.slot);
		sendBuffer.writeBoolean(message.shift);
		sendBuffer.writeItemStack(message.stack, false);
	}
	
	public static FluidClickContainerMessage decode(PacketBuffer sendBuffer) {
		final int id = sendBuffer.readByte();
		final int slot = sendBuffer.readShort();
		final boolean shift = sendBuffer.readBoolean();
		final ItemStack stack = sendBuffer.readItemStack();
		
		return new FluidClickContainerMessage(id, slot, shift, stack);
	}
	
	public static class Handler {
		
		public static void handle(FluidClickContainerMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				final ServerPlayerEntity player = context.getSender();
				getFluidContainer(player.openContainer, message.id).ifPresent(container -> container.fluidSlotClick(player, message.slot, message.shift, message.stack));
			});
			context.setPacketHandled(true);
		}
		
		private static final Optional<FluidContainer> getFluidContainer(Container container, int id) {
			if (container instanceof FluidContainer && container.windowId == id) {
				return Optional.of((FluidContainer) container);
			}
			return Optional.empty();
		}
	}
	
}