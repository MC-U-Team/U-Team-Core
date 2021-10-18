package info.u_team.u_team_core.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import info.u_team.u_team_core.api.sync.DataHolder;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.BufferPropertyContainerMessage;
import info.u_team.u_team_core.screen.UContainerScreen;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

/**
 * A basic menu with synchronization capabilities that implements the {@link FluidContainerMenu}.
 *
 * @author HyCraftHD
 */
public abstract class UContainerMenu extends FluidContainerMenu {
	
	/**
	 * Server -> Client
	 */
	private final List<DataHolder> syncServerToClient;
	
	/**
	 * Client -> Server
	 */
	private final List<DataHolder> syncClientToServer;
	
	/**
	 * Creates a new container. Must be implemented by a sub class to be used.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 */
	protected UContainerMenu(MenuType<?> menuType, int containerId) {
		super(menuType, containerId);
		syncServerToClient = new ArrayList<>();
		syncClientToServer = new ArrayList<>();
	}
	
	/**
	 * Adds a new {@link DataHolder} that will sync values from the server to the client.
	 *
	 * @param holder Buffer reference holder
	 * @return The buffer reference holder
	 */
	protected <E extends DataHolder> E addServerToClientTracker(E holder) {
		syncServerToClient.add(holder);
		return holder;
	}
	
	/**
	 * Adds a new {@link DataHolder} that will sync values from the client to the server. <br />
	 * <br />
	 * THE AUTO SYNC ONLY WORKS IF YOU USE AN IMPLEMENTION OF {@link UContainerScreen}. If not you must manually call
	 * {@link #updateTrackedServerToClient()} every time you update values on the client that should be synced to the
	 * server.
	 *
	 * @param holder Buffer reference holder
	 * @return The buffer reference holder
	 */
	protected <E extends DataHolder> E addClientToServerTracker(E holder) {
		syncClientToServer.add(holder);
		return holder;
	}
	
	/**
	 * INTERNAL. Called by the packet handler to update the values on the right side.
	 *
	 * @param message Message
	 * @param side Side to handle the packet on
	 */
	public void updateValue(BufferPropertyContainerMessage message, LogicalSide side) {
		final var property = message.getProperty();
		final var buffer = message.getBuffer();
		if (side == LogicalSide.CLIENT) {
			syncServerToClient.get(property).set(buffer);
		} else if (side == LogicalSide.SERVER) {
			syncClientToServer.get(property).set(buffer);
		}
	}
	
	/**
	 * We use this method to send the tracked values to the client
	 */
	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		
		final List<Connection> networkManagers = containerListeners.stream() //
				.filter(listener -> listener instanceof ServerPlayer) //
				.map(listener -> ((ServerPlayer) listener).connection.getConnection()) //
				.collect(Collectors.toList());
		getDirtyMap(syncServerToClient).forEach((property, holder) -> {
			UCoreNetwork.NETWORK.send(PacketDistributor.NMLIST.with(() -> networkManagers), new BufferPropertyContainerMessage(containerId, property, holder.get()));
		});
	}
	
	/**
	 * We use this method to send the tracked values to the server
	 *
	 * @see #addClientToServerTracker(DataHolder)
	 */
	public void updateTrackedServerToClient() {
		getDirtyMap(syncClientToServer).forEach((property, holder) -> {
			UCoreNetwork.NETWORK.send(PacketDistributor.SERVER.noArg(), new BufferPropertyContainerMessage(containerId, property, holder.get()));
		});
	}
	
	/**
	 * Returns a map with all {@link DataHolder} that are dirty. The key is the property index.
	 *
	 * @param list The list of {@link DataHolder}
	 * @return A map with dirty values
	 */
	private Map<Integer, DataHolder> getDirtyMap(List<DataHolder> list) {
		return IntStream.range(0, list.size()) //
				.filter(index -> list.get(index).checkAndClearUpdateFlag()) //
				.boxed() //
				.collect(Collectors.toMap(Function.identity(), index -> list.get(index)));
	}
	
	/**
	 * Player can interact with this container
	 */
	@Override
	public boolean stillValid(Player player) {
		return true;
	}
}
