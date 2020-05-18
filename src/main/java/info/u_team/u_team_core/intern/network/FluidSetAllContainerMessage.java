package info.u_team.u_team_core.intern.network;

import java.util.*;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.*;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class FluidSetAllContainerMessage {
	
	private final int id;
	private List<FluidStack> stacks;
	
	public FluidSetAllContainerMessage(int id, List<FluidStack> stacks) {
		this.id = id;
		this.stacks = stacks;
	}
	
	public static void encode(FluidSetAllContainerMessage message, PacketBuffer sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.stacks.size());
		for (FluidStack stack : message.stacks) {
			sendBuffer.writeFluidStack(stack);
		}
	}
	
	public static FluidSetAllContainerMessage decode(PacketBuffer sendBuffer) {
		final int id = sendBuffer.readByte();
		final int size = sendBuffer.readShort();
		final List<FluidStack> stacks = NonNullList.withSize(size, FluidStack.EMPTY);
		for (int index = 0; index < size; index++) {
			stacks.set(index, sendBuffer.readFluidStack());
		}
		return new FluidSetAllContainerMessage(id, stacks);
	}
	
	public static class Handler {
		
		public static void handle(FluidSetAllContainerMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				getFluidContainer(Minecraft.getInstance().player.openContainer, message.id).ifPresent(container -> container.setAllFluidSlots(message.stacks));
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
