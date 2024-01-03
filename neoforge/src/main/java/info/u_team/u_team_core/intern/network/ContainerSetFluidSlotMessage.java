package info.u_team.u_team_core.intern.network;

import java.util.Optional;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.NeoForgeFluidContainerMenuDelegator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ContainerSetFluidSlotMessage implements CustomPacketPayload {
	
	public static final ResourceLocation ID = new ResourceLocation(UCoreMod.MODID, "container_set_fluid_slot");
	
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
	public ResourceLocation id() {
		return ID;
	}
	
	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeByte(containerId);
		buffer.writeVarInt(stateId);
		buffer.writeShort(slot);
		buffer.writeFluidStack(stack);
	}
	
	public static ContainerSetFluidSlotMessage read(FriendlyByteBuf buffer) {
		final byte containerId = buffer.readByte();
		final int stateId = buffer.readVarInt();
		final short slot = buffer.readShort();
		final FluidStack stack = buffer.readFluidStack();
		
		return new ContainerSetFluidSlotMessage(containerId, stateId, slot, stack);
	}
	
	public static class Handler {
		
		public static void handle(ContainerSetFluidSlotMessage message, PlayPayloadContext context) {
			context.workHandler().execute(() -> {
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
