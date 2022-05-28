package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.menu.FluidContainerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class ContainerSetFluidSlotMessage {
	
	private final int containerId;
	private final int stateId;
	private final int slot;
	private final FluidStack stack;
	
	public ContainerSetFluidSlotMessage(int containerId, int stateId, int slot, FluidStack stack) {
		this.containerId = containerId;
		this.stateId = stateId;
		this.slot = slot;
		this.stack = stack.copy();
	}
	
	public static void encode(ContainerSetFluidSlotMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.containerId);
		byteBuf.writeVarInt(message.stateId);
		byteBuf.writeShort(message.slot);
		byteBuf.writeFluidStack(message.stack);
	}
	
	public static ContainerSetFluidSlotMessage decode(FriendlyByteBuf byteBuf) {
		final byte containerId = byteBuf.readByte();
		final int stateId = byteBuf.readVarInt();
		final short slot = byteBuf.readShort();
		final FluidStack stack = byteBuf.readFluidStack();
		
		return new ContainerSetFluidSlotMessage(containerId, stateId, slot, stack);
	}
	
	public static class Handler {
		
		public static void handle(ContainerSetFluidSlotMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				testContainerMenu(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(container -> container.setFluid(message.slot, message.stateId, message.stack));
			});
			context.setPacketHandled(true);
		}
		
		private static final Optional<FluidContainerMenu> testContainerMenu(AbstractContainerMenu container, int containerId) {
			if (container instanceof FluidContainerMenu fluidMenu && container.containerId == containerId) {
				return Optional.of(fluidMenu);
			}
			return Optional.empty();
		}
	}
}
