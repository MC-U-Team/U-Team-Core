package info.u_team.u_team_core.intern.network;

import java.util.Optional;

import info.u_team.u_team_core.UCoreMod;
import info.u_team.u_team_core.menu.FluidContainerMenu;
import info.u_team.u_team_core.menu.NeoForgeFluidContainerMenuDelegator;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class FluidClickContainerMessage implements CustomPacketPayload {
	
	public static final Type<FluidClickContainerMessage> TYPE = new Type<>(new ResourceLocation(UCoreMod.MODID, "fluid_click_container"));
	
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
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
	
	public static void write(RegistryFriendlyByteBuf buffer, FluidClickContainerMessage message) {
		buffer.writeByte(message.id);
		buffer.writeShort(message.slot);
		buffer.writeBoolean(message.shift);
		ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, message.stack);
	}
	
	public static FluidClickContainerMessage read(RegistryFriendlyByteBuf buffer) {
		final int id = buffer.readByte();
		final int slot = buffer.readShort();
		final boolean shift = buffer.readBoolean();
		final ItemStack stack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
		
		return new FluidClickContainerMessage(id, slot, shift, stack);
	}
	
	public static class Handler {
		
		public static void handle(FluidClickContainerMessage message, IPayloadContext context) {
			context.enqueueWork(() -> {
				final ServerPlayer player = (ServerPlayer) context.player();
				getFluidContainer(player.containerMenu, message.id).ifPresent(menu -> ((NeoForgeFluidContainerMenuDelegator) (menu.getDelegator())).fluidSlotClick(player, message.slot, message.shift, message.stack));
			});
		}
		
		private static final Optional<FluidContainerMenu> getFluidContainer(AbstractContainerMenu container, int id) {
			if (container instanceof FluidContainerMenu && container.containerId == id) {
				return Optional.of((FluidContainerMenu) container);
			}
			return Optional.empty();
		}
	}
	
}
