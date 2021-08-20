package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.FluidContainerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class FluidSetSlotContainerMessage {
	
	private final int containerId;
	private final int stateId;
	private final int slot;
	private final FluidStack stack;
	
	public FluidSetSlotContainerMessage(int containerId, int stateId, int slot, FluidStack stack) {
		this.containerId = containerId;
		this.stateId = stateId;
		this.slot = slot;
		this.stack = stack;
	}
	
	public static void encode(FluidSetSlotContainerMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.containerId);
		byteBuf.writeVarInt(message.stateId);
		byteBuf.writeShort(message.slot);
		byteBuf.writeFluidStack(message.stack);
	}
	
	public static FluidSetSlotContainerMessage decode(FriendlyByteBuf byteBuf) {
		final var containerId = byteBuf.readByte();
		final var stateId = byteBuf.readVarInt();
		final var slot = byteBuf.readShort();
		final var stack = byteBuf.readFluidStack();
		
		return new FluidSetSlotContainerMessage(containerId, stateId, slot, stack);
	}
	
	public static class Handler {
		
		public static void handle(FluidSetSlotContainerMessage message, Supplier<Context> contextSupplier) {
			final var context = contextSupplier.get();
			context.enqueueWork(() -> {
				getFluidContainer(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(container -> container.setFluid(message.slot, message.stateId, message.stack));
			});
			context.setPacketHandled(true);
		}
		
		private static final Optional<FluidContainerMenu> getFluidContainer(AbstractContainerMenu container, int containerId) {
			if (container instanceof FluidContainerMenu && container.containerId == containerId) {
				return Optional.of((FluidContainerMenu) container);
			}
			return Optional.empty();
		}
	}
}
