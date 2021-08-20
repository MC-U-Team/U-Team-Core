package info.u_team.u_team_core.intern.network;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.container.FluidContainerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public class FluidSetAllContainerMessage {
	
	private final int id;
	private final List<FluidStack> stacks;
	
	public FluidSetAllContainerMessage(int id, List<FluidStack> stacks) {
		this.id = id;
		this.stacks = stacks;
	}
	
	public static void encode(FluidSetAllContainerMessage message, FriendlyByteBuf sendBuffer) {
		sendBuffer.writeByte(message.id);
		sendBuffer.writeShort(message.stacks.size());
		for (final FluidStack stack : message.stacks) {
			sendBuffer.writeFluidStack(stack);
		}
	}
	
	public static FluidSetAllContainerMessage decode(FriendlyByteBuf sendBuffer) {
		final int id = sendBuffer.readByte();
		final int size = sendBuffer.readShort();
		final List<FluidStack> stacks = NonNullList.withSize(size, FluidStack.EMPTY);
		for (var index = 0; index < size; index++) {
			stacks.set(index, sendBuffer.readFluidStack());
		}
		return new FluidSetAllContainerMessage(id, stacks);
	}
	
	public static class Handler {
		
		public static void handle(FluidSetAllContainerMessage message, Supplier<Context> contextSupplier) {
			final var context = contextSupplier.get();
			context.enqueueWork(() -> {
				getFluidContainer(Minecraft.getInstance().player.containerMenu, message.id).ifPresent(container -> container.setAllFluidSlots(message.stacks));
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
