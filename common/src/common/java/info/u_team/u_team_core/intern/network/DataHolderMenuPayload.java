package info.u_team.u_team_core.intern.network;

import java.util.Optional;
import java.util.Set;

import info.u_team.u_team_core.api.network.NetworkContext;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.api.network.NetworkPayload;
import info.u_team.u_team_core.intern.network.DataHolderMenuPayload.DataHolderMenuMessage;
import info.u_team.u_team_core.menu.UContainerMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class DataHolderMenuPayload implements NetworkPayload<DataHolderMenuMessage> {
	
	public static record DataHolderMenuMessage(int containerId, int index, FriendlyByteBuf dataHolderBuffer) {
	}
	
	@Override
	public Set<NetworkEnvironment> getHandlerEnvironment() {
		return NetworkEnvironment.BOTH;
	}
	
	@Override
	public void write(DataHolderMenuMessage message, FriendlyByteBuf buffer) {
		buffer.writeByte(message.containerId);
		buffer.writeShort(message.index);
		buffer.writeVarInt(message.dataHolderBuffer.readableBytes());
		buffer.writeBytes(message.dataHolderBuffer);
		message.dataHolderBuffer.release();
	}
	
	@Override
	public DataHolderMenuMessage read(FriendlyByteBuf buffer) {
		final int containerId = buffer.readByte();
		final int index = buffer.readShort();
		final FriendlyByteBuf dataHolderBuffer = new FriendlyByteBuf(Unpooled.wrappedBuffer(buffer.readByteArray()));
		return new DataHolderMenuMessage(containerId, index, dataHolderBuffer);
	}
	
	@Override
	public void handle(DataHolderMenuMessage message, NetworkContext context) {
		context.executeOnMainThread(() -> {
			final Player player = context.getPlayer();
			try {
				if (player != null) {
					updateDataHolder(context.getPlayer().containerMenu, context.getEnvironment(), message);
				}
			} finally {
				message.dataHolderBuffer.release();
			}
		});
	}
	
	private void updateDataHolder(AbstractContainerMenu menuToTest, NetworkEnvironment environment, DataHolderMenuMessage message) {
		testContainerMenu(menuToTest, message.containerId).ifPresent(menu -> menu.setDataHolder(environment, message.index, message.dataHolderBuffer));
	}
	
	private Optional<UContainerMenu> testContainerMenu(AbstractContainerMenu menu, int containerId) {
		if (menu instanceof final UContainerMenu uContainer && menu.containerId == containerId) {
			return Optional.of(uContainer);
		}
		return Optional.empty();
	}
}
