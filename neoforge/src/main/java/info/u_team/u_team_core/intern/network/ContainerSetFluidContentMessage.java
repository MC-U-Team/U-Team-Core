package info.u_team.u_team_core.intern.network;

import java.util.List;
import java.util.Optional;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.NeoForgeFluidContainerMenuDelegator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ContainerSetFluidContentMessage implements CustomPacketPayload {
	
	public static final Type<ContainerSetFluidContentMessage> TYPE = new Type<>(new ResourceLocation(UCoreMod.MODID, "container_set_fluid_content"));
	
	private final int containerId;
	private final int stateId;
	private final List<FluidStack> fluids;
	
	public ContainerSetFluidContentMessage(int containerId, int stateId, List<FluidStack> fluids) {
		this.containerId = containerId;
		this.stateId = stateId;
		
		this.fluids = NonNullList.withSize(fluids.size(), FluidStack.EMPTY);
		
		for (int index = 0; index < fluids.size(); index++) {
			this.fluids.set(index, fluids.get(index).copy());
		}
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
	public static void write(RegistryFriendlyByteBuf buffer, ContainerSetFluidContentMessage message) {
		buffer.writeByte(message.containerId);
		buffer.writeVarInt(message.stateId);
		buffer.writeCollection(message.fluids, (__, value) -> FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, value));
	}
	
	public static ContainerSetFluidContentMessage read(RegistryFriendlyByteBuf buffer) {
		final byte containerId = buffer.readByte();
		final int stateId = buffer.readVarInt();
		final List<FluidStack> fluids = buffer.readCollection(NonNullList::createWithCapacity, __ -> FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer));
		return new ContainerSetFluidContentMessage(containerId, stateId, fluids);
	}
	
	public static class Handler {
		
		public static void handle(ContainerSetFluidContentMessage message, IPayloadContext context) {
			context.enqueueWork(() -> {
				testContainerMenu(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(menu -> ((NeoForgeFluidContainerMenuDelegator) (menu.getDelegator())).initializeFluidContents(message.stateId, message.fluids));
			});
		}
		
		private static final Optional<FluidContainerMenu> testContainerMenu(AbstractContainerMenu menu, int containerId) {
			if (menu instanceof final FluidContainerMenu fluidMenu && menu.containerId == containerId) {
				return Optional.of(fluidMenu);
			}
			return Optional.empty();
		}
	}
}
