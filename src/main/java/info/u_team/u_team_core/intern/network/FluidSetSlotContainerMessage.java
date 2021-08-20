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
	
	private final int id;
	private final int slot;
	private final FluidStack stack;
	
	public FluidSetSlotContainerMessage(int id, int slot, FluidStack stack) {
		this.id = id;
		this.slot = slot;
		this.stack = stack;
	}
	
	public static void encode(FluidSetSlotContainerMessage message, FriendlyByteBuf sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.slot);
		sendBuffer.writeFluidStack(message.stack);
	}
	
	public static FluidSetSlotContainerMessage decode(FriendlyByteBuf sendBuffer) {
		final int id = sendBuffer.readByte();
		final int slot = sendBuffer.readShort();
		final var stack = sendBuffer.readFluidStack();
		
		return new FluidSetSlotContainerMessage(id, slot, stack);
	}
	
	public static class Handler {
		
		public static void handle(FluidSetSlotContainerMessage message, Supplier<Context> contextSupplier) {
			final var context = contextSupplier.get();
			context.enqueueWork(() -> {
				getFluidContainer(Minecraft.getInstance().player.containerMenu, message.id).ifPresent(container -> container.setFluidStackInSlot(message.slot, message.stack));
			});
			context.setPacketHandled(true);
		}
		
		private static final Optional<FluidContainerMenu> getFluidContainer(AbstractContainerMenu container, int id) {
			if (container instanceof FluidContainerMenu && container.containerId == id) {
				return Optional.of((FluidContainerMenu) container);
			}
			return Optional.empty();
		}
	}
}
