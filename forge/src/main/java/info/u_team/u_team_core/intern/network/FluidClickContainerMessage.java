package info.u_team.u_team_core.intern.network;

import java.util.Optional;

import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.ForgeFluidContainerMenuDelegator;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent.Context;

public class FluidClickContainerMessage {
	
	private final int id;
	private final int slot;
	private final boolean shift;
	private final ItemStack stack;
	
	public FluidClickContainerMessage(int id, int slot, boolean shift, ItemStack stack) {
		this.id = id;
		this.slot = slot;
		this.shift = shift;
		this.stack = stack;
	}
	
	public static void encode(FluidClickContainerMessage message, RegistryFriendlyByteBuf byteBuf) {
		byteBuf.writeByte(message.id);
		byteBuf.writeShort(message.slot);
		byteBuf.writeBoolean(message.shift);
		ItemStack.OPTIONAL_STREAM_CODEC.encode(byteBuf, message.stack);
	}
	
	public static FluidClickContainerMessage decode(RegistryFriendlyByteBuf byteBuf) {
		final int id = byteBuf.readByte();
		final int slot = byteBuf.readShort();
		final boolean shift = byteBuf.readBoolean();
		final ItemStack stack = ItemStack.OPTIONAL_STREAM_CODEC.decode(byteBuf);
		
		return new FluidClickContainerMessage(id, slot, shift, stack);
	}
	
	public static class Handler {
		
		public static void handle(FluidClickContainerMessage message, Context context) {
			context.enqueueWork(() -> {
				final ServerPlayer player = context.getSender();
				getFluidContainer(player.containerMenu, message.id).ifPresent(menu -> ((ForgeFluidContainerMenuDelegator) (menu.getDelegator())).fluidSlotClick(player, message.slot, message.shift, message.stack));
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
