package info.u_team.u_team_core.intern.network;

import java.util.Optional;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.menu.UContainerMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public record DataHolderMenuMessage(int containerId, int index, byte[] dataHolderBuffer) {
	
	public static final StreamCodec<RegistryFriendlyByteBuf, DataHolderMenuMessage> STREAM_CODEC = StreamCodec.composite( //
			ByteBufCodecs.VAR_INT, DataHolderMenuMessage::containerId, //
			ByteBufCodecs.VAR_INT, DataHolderMenuMessage::index, //
			ByteBufCodecs.BYTE_ARRAY, DataHolderMenuMessage::dataHolderBuffer, //
			DataHolderMenuMessage::new);
	
	public static void handle(DataHolderMenuMessage message, NetworkContext context) {
		context.executeOnMainThread(() -> {
			final Player player = context.getPlayer();
			if (player != null) {
				updateDataHolder(player.containerMenu, context.getEnvironment(), message);
			}
		});
	}
	
	private static void updateDataHolder(AbstractContainerMenu menuToTest, NetworkEnvironment environment, DataHolderMenuMessage message) {
		testContainerMenu(menuToTest, message.containerId).ifPresent(menu -> {
			final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(message.dataHolderBuffer));
			try {
				menu.setDataHolder(environment, message.index, buffer);
			} finally {
				buffer.release();
			}
		});
	}
	
	private static Optional<UContainerMenu> testContainerMenu(AbstractContainerMenu menu, int containerId) {
		if (menu instanceof final UContainerMenu uContainer && menu.containerId == containerId) {
			return Optional.of(uContainer);
		}
		return Optional.empty();
	}
}
