package info.u_team.u_team_core.intern.network;

import java.util.Optional;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.NeoForgeFluidContainerMenuDelegator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ContainerSetFluidSlotMessage implements CustomPacketPayload {
	
	public static final Type<ContainerSetFluidSlotMessage> TYPE = new Type<>(new ResourceLocation(UCoreMod.MODID, "container_set_fluid_slot"));
	
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
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
	public static void write(RegistryFriendlyByteBuf buffer, ContainerSetFluidSlotMessage message) {
		buffer.writeByte(message.containerId);
		buffer.writeVarInt(message.stateId);
		buffer.writeShort(message.slot);
		FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, message.stack);
	}
	
	public static ContainerSetFluidSlotMessage read(RegistryFriendlyByteBuf buffer) {
		final byte containerId = buffer.readByte();
		final int stateId = buffer.readVarInt();
		final short slot = buffer.readShort();
		final FluidStack stack = FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer);
		
		return new ContainerSetFluidSlotMessage(containerId, stateId, slot, stack);
	}
	
	public static class Handler {
		
		public static void handle(ContainerSetFluidSlotMessage message, IPayloadContext context) {
			context.enqueueWork(() -> {
				testContainerMenu(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(menu -> ((NeoForgeFluidContainerMenuDelegator) (menu.getDelegator())).setFluid(message.slot, message.stateId, message.stack));
			});
		}
		
		private static final Optional<FluidContainerMenu> testContainerMenu(AbstractContainerMenu container, int containerId) {
			if (container instanceof final FluidContainerMenu fluidMenu && container.containerId == containerId) {
				return Optional.of(fluidMenu);
			}
			return Optional.empty();
		}
	}
}
