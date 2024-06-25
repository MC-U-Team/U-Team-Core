package info.u_team.u_team_core.intern.network;

import java.util.List;
import java.util.Optional;

import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.ForgeFluidContainerMenuDelegator;
import info.u_team.u_team_core.util.SerializeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;
import net.minecraftforge.fluids.FluidStack;

public class ContainerSetFluidContentMessage {
	
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
	
	public static void encode(ContainerSetFluidContentMessage message, RegistryFriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.containerId);
		byteBuf.writeVarInt(message.stateId);
		byteBuf.writeCollection(message.fluids, (__, value) -> SerializeUtil.FLUID_STACK_STREAM_CODEC.encode(byteBuf, value));
	}
	
	public static ContainerSetFluidContentMessage decode(RegistryFriendlyByteBuf byteBuf) {
		final byte containerId = byteBuf.readByte();
		final int stateId = byteBuf.readVarInt();
		final List<FluidStack> fluids = byteBuf.readCollection(NonNullList::createWithCapacity, __ -> SerializeUtil.FLUID_STACK_STREAM_CODEC.decode(byteBuf));
		return new ContainerSetFluidContentMessage(containerId, stateId, fluids);
	}
	
	public static class Handler {
		
		public static void handle(ContainerSetFluidContentMessage message, Context context) {
			context.enqueueWork(() -> {
				testContainerMenu(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(menu -> ((ForgeFluidContainerMenuDelegator) (menu.getDelegator())).initializeFluidContents(message.stateId, message.fluids));
			});
			context.setPacketHandled(true);
		}
		
		private static final Optional<FluidContainerMenu> testContainerMenu(AbstractContainerMenu menu, int containerId) {
			if (menu instanceof final FluidContainerMenu fluidMenu && menu.containerId == containerId) {
				return Optional.of(fluidMenu);
			}
			return Optional.empty();
		}
	}
}
