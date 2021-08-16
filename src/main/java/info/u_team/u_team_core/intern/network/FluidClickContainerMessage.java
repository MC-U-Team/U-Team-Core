package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.FluidContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

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

	public static void encode(FluidClickContainerMessage message, FriendlyByteBuf sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.slot);
		sendBuffer.writeBoolean(message.shift);
		sendBuffer.writeItemStack(message.stack, false);
	}

	public static FluidClickContainerMessage decode(FriendlyByteBuf sendBuffer) {
		final int id = sendBuffer.readByte();
		final int slot = sendBuffer.readShort();
		final var shift = sendBuffer.readBoolean();
		final var stack = sendBuffer.readItem();

		return new FluidClickContainerMessage(id, slot, shift, stack);
	}

	public static class Handler {

		public static void handle(FluidClickContainerMessage message, Supplier<Context> contextSupplier) {
			final var context = contextSupplier.get();
			context.enqueueWork(() -> {
				final var player = context.getSender();
				getFluidContainer(player.containerMenu, message.id).ifPresent(container -> container.fluidSlotClick(player, message.slot, message.shift, message.stack));
			});
			context.setPacketHandled(true);
		}

		private static final Optional<FluidContainer> getFluidContainer(AbstractContainerMenu container, int id) {
			if (container instanceof FluidContainer && container.containerId == id) {
				return Optional.of((FluidContainer) container);
			}
			return Optional.empty();
		}
	}

}
