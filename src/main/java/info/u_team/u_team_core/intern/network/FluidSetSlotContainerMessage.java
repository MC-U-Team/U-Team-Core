package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.FluidContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class FluidSetSlotContainerMessage {
	
	private final int id;
	private final int slot;
	private final FluidStack stack;
	
	public FluidSetSlotContainerMessage(int id, int slot, FluidStack stack) {
		this.id = id;
		this.slot = slot;
		this.stack = stack;
	}
	
	public static void encode(FluidSetSlotContainerMessage message, PacketBuffer sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.slot);
		sendBuffer.writeFluidStack(message.stack);
	}
	
	public static FluidSetSlotContainerMessage decode(PacketBuffer sendBuffer) {
		final int id = sendBuffer.readByte();
		final int slot = sendBuffer.readShort();
		final FluidStack stack = sendBuffer.readFluidStack();
		
		return new FluidSetSlotContainerMessage(id, slot, stack);
	}
	
	public static class Handler {
		
		public static void handle(FluidSetSlotContainerMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				getFluidContainer(Minecraft.getInstance().player.openContainer, message.id).ifPresent(container -> container.setFluidStackInSlot(message.slot, message.stack));
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
