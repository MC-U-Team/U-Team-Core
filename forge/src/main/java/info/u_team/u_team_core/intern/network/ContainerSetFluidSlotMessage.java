package info.u_team.u_team_core.intern.network;

import java.util.Optional;

import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.ForgeFluidContainerMenuDelegator;
import info.u_team.u_team_core.util.SerializeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;
import net.minecraftforge.fluids.FluidStack;

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
	
	public static void encode(ContainerSetFluidSlotMessage message, RegistryFriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.containerId);
		byteBuf.writeVarInt(message.stateId);
		byteBuf.writeShort(message.slot);
		SerializeUtil.FLUID_STACK_STREAM_CODEC.encode(byteBuf, message.stack);
	}
	
	public static ContainerSetFluidSlotMessage decode(RegistryFriendlyByteBuf byteBuf) {
		final byte containerId = byteBuf.readByte();
		final int stateId = byteBuf.readVarInt();
		final short slot = byteBuf.readShort();
		final FluidStack stack = SerializeUtil.FLUID_STACK_STREAM_CODEC.decode(byteBuf);
		
		return new ContainerSetFluidSlotMessage(containerId, stateId, slot, stack);
	}
	
	public static class Handler {
		
		public static void handle(ContainerSetFluidSlotMessage message, Context context) {
			context.enqueueWork(() -> {
				testContainerMenu(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(menu -> ((ForgeFluidContainerMenuDelegator) (menu.getDelegator())).setFluid(message.slot, message.stateId, message.stack));
			});
			context.setPacketHandled(true);
		}
		
		private static final Optional<FluidContainerMenu> testContainerMenu(AbstractContainerMenu container, int containerId) {
			if (container instanceof final FluidContainerMenu fluidMenu && container.containerId == containerId) {
				return Optional.of(fluidMenu);
			}
			return Optional.empty();
		}
	}
}
