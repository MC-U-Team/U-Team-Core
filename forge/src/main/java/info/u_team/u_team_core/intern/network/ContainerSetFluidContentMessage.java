package info.u_team.u_team_core.intern.network;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.menu.FluidContainerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent.Context;

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
	
	public static void encode(ContainerSetFluidContentMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.containerId);
		byteBuf.writeVarInt(message.stateId);
		byteBuf.writeCollection(message.fluids, FriendlyByteBuf::writeFluidStack);
	}
	
	public static ContainerSetFluidContentMessage decode(FriendlyByteBuf byteBuf) {
		final byte containerId = byteBuf.readByte();
		final int stateId = byteBuf.readVarInt();
		final List<FluidStack> fluids = byteBuf.readCollection(NonNullList::createWithCapacity, FriendlyByteBuf::readFluidStack);
		return new ContainerSetFluidContentMessage(containerId, stateId, fluids);
	}
	
	public static class Handler {
		
		public static void handle(ContainerSetFluidContentMessage message, Supplier<Context> contextSupplier) {
			final Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				testContainerMenu(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(menu -> menu.initializeFluidContents(message.stateId, message.fluids));
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
