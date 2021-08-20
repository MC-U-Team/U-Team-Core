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
	
	private final int containerId;
	private final int stateId;
	private final List<FluidStack> stacks;
	
	public FluidSetAllContainerMessage(int containerId, int stateId, List<FluidStack> stacks) {
		this.containerId = containerId;
		this.stateId = stateId;
		this.stacks = stacks;
	}
	
	public static void encode(FluidSetAllContainerMessage message, FriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.containerId);
		byteBuf.writeVarInt(message.stateId);
		byteBuf.writeCollection(message.stacks, FriendlyByteBuf::writeFluidStack);
	}
	
	public static FluidSetAllContainerMessage decode(FriendlyByteBuf byteBuf) {
		final var containerId = byteBuf.readByte();
		final var stateId = byteBuf.readVarInt();
		final var stacks = byteBuf.readCollection(NonNullList::createWithCapacity, FriendlyByteBuf::readFluidStack);
		return new FluidSetAllContainerMessage(containerId, stateId, stacks);
	}
	
	public static class Handler {
		
		public static void handle(FluidSetAllContainerMessage message, Supplier<Context> contextSupplier) {
			final var context = contextSupplier.get();
			context.enqueueWork(() -> {
				getFluidContainer(Minecraft.getInstance().player.containerMenu, message.containerId).ifPresent(menu -> menu.initializeFluidContents(message.stateId, message.stacks));
			});
			context.setPacketHandled(true);
		}
		
		private static final Optional<FluidContainerMenu> getFluidContainer(AbstractContainerMenu menu, int id) {
			if (menu instanceof FluidContainerMenu fluidMenu && menu.containerId == id) {
				return Optional.of(fluidMenu);
			}
			return Optional.empty();
		}
	}
}
