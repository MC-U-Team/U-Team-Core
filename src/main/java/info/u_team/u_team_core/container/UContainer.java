package info.u_team.u_team_core.container;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import info.u_team.u_team_core.api.sync.BufferReferenceHolder;
import info.u_team.u_team_core.gui.UContainerScreen;
import info.u_team.u_team_core.intern.init.UCoreNetwork;
import info.u_team.u_team_core.intern.network.BufferPropertyContainerMessage;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.*;

/**
 * A basic container
 * 
 * @author HyCraftHD
 */
public abstract class UContainer extends Container {
	
	/**
	 * Server -> Client
	 */
	private final List<BufferReferenceHolder> syncServerToClient;
	
	/**
	 * Client -> Server
	 */
	private final List<BufferReferenceHolder> syncClientToServer;
	
	/**
	 * Creates a new container
	 * 
	 * @param type Container type
	 * @param id Window id
	 */
	public UContainer(ContainerType<?> type, int id) {
		super(type, id);
		syncServerToClient = new ArrayList<>();
		syncClientToServer = new ArrayList<>();
	}
	
	/**
	 * Adds a new {@link BufferReferenceHolder} that will sync values from the server to the client.
	 * 
	 * @param holder Buffer reference holder
	 */
	protected void addServerToClientTracker(BufferReferenceHolder holder) {
		syncServerToClient.add(holder);
	}
	
	/**
	 * Adds a new {@link BufferReferenceHolder} that will sync values from the client to the server. <br />
	 * <br />
	 * THE AUTO SYNC ONLY WORKS IF YOU USE AN IMPLEMENTION OF {@link UContainerScreen}. If not you must manually call
	 * {@link #updateTrackedServerToClient()} every time you update values on the client that should be synced to the
	 * server.
	 * 
	 * @param holder Buffer reference holder
	 */
	protected void addClientToServerTracker(BufferReferenceHolder holder) {
		syncClientToServer.add(holder);
	}
	
	/**
	 * INTERNAL. Called by the packet handler to update the values on the right side.
	 * 
	 * @param message Message
	 * @param side Side to handle the packet on
	 */
	public void updateValue(BufferPropertyContainerMessage message, LogicalSide side) {
		final int property = message.getProperty();
		final PacketBuffer buffer = message.getBuffer();
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
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		final List<NetworkManager> networkManagers = listeners.stream() //
				.filter(listener -> listener instanceof ServerPlayerEntity) //
				.map(listener -> ((ServerPlayerEntity) listener).connection.getNetworkManager()) //
				.collect(Collectors.toList());
		getDirtyMap(syncServerToClient).forEach((property, holder) -> {
			UCoreNetwork.NETWORK.send(PacketDistributor.NMLIST.with(() -> networkManagers), new BufferPropertyContainerMessage(windowId, property, holder.get()));
		});
	}
	
	/**
	 * We use this method to send the tracked values to the server
	 * 
	 * @see #addClientToServerTracker(BufferReferenceHolder)
	 */
	public void updateTrackedServerToClient() {
		getDirtyMap(syncClientToServer).forEach((property, holder) -> {
			UCoreNetwork.NETWORK.send(PacketDistributor.SERVER.noArg(), new BufferPropertyContainerMessage(windowId, property, holder.get()));
		});
	}
	
	/**
	 * Returns a map with all {@link BufferReferenceHolder} that are dirty. The key is the property index.
	 * 
	 * @param list The list of {@link BufferReferenceHolder}
	 * @return A map with dirty values
	 */
	private Map<Integer, BufferReferenceHolder> getDirtyMap(List<BufferReferenceHolder> list) {
		return IntStream.range(0, list.size()) //
				.filter(index -> list.get(index).isDirty()) //
				.boxed() //
				.collect(Collectors.toMap(Function.identity(), index -> list.get(index)));
	}
	
	/**
	 * Player can interact with this container
	 */
	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return true;
	}
	
	/**
	 * This methods adds a player inventory to the container.
	 * 
	 * @param playerInventory Player inventory
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendPlayerInventory(PlayerInventory playerInventory, int x, int y) {
		for (int height = 0; height < 4; height++) {
			for (int width = 0; width < 9; width++) {
				if (height == 3) {
					addSlot(new Slot(playerInventory, width, width * 18 + x, height * 18 + 4 + y));
					continue;
				}
				addSlot(new Slot(playerInventory, width + height * 9 + 9, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns).
	 * 
	 * @param inventory Some inventory
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IInventory inventory, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(inventory, Slot::new, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IInventory} to the container. You can specialize the inventory height (slot rows) and
	 * width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 * 
	 * @param inventory Some inventory
	 * @param function Function to create a slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IInventory inventory, SlotInventoryFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (int height = 0; height < inventoryHeight; height++) {
			for (int width = 0; width < inventoryWidth; width++) {
				addSlot(function.getSlot(inventory, width + height * inventoryWidth, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns).
	 * 
	 * @param handler Some item handler
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IItemHandler handler, int inventoryHeight, int inventoryWidth, int x, int y) {
		appendInventory(handler, SlotItemHandler::new, inventoryHeight, inventoryWidth, x, y);
	}
	
	/**
	 * This methods can add any {@link IItemHandler} to the container. You can specialize the inventory height (slot rows)
	 * and width (slot columns). You must supplier a function that create a slot. With this you can set your own slot.
	 * implementations.
	 * 
	 * @param handler Some item handler
	 * @param function Function to create a slot.
	 * @param inventoryHeight Slot rows
	 * @param inventoryWidth Slot columns
	 * @param x Start x
	 * @param y Start y
	 */
	protected void appendInventory(IItemHandler handler, SlotHandlerFunction function, int inventoryHeight, int inventoryWidth, int x, int y) {
		for (int height = 0; height < inventoryHeight; height++) {
			for (int width = 0; width < inventoryWidth; width++) {
				addSlot(function.getSlot(handler, width + height * inventoryWidth, width * 18 + x, height * 18 + y));
			}
		}
	}
	
	/**
	 * Used as a function to customize slots with the append methods
	 * 
	 * @author HyCraftHD
	 */
	@FunctionalInterface
	public static interface SlotInventoryFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 * 
		 * @param inventory An inventory
		 * @param index Index for this inventory
		 * @param xPosition x coordinate
		 * @param yPosition y coordinate
		 * @return A slot instance
		 */
		Slot getSlot(IInventory inventory, int index, int xPosition, int yPosition);
	}
	
	/**
	 * Used as a function to customize slots with the append methods
	 * 
	 * @author HyCraftHD
	 */
	@FunctionalInterface
	public static interface SlotHandlerFunction {
		
		/**
		 * Should return a slot with the applied parameters.
		 * 
		 * @param itemHandler An item handler
		 * @param index Index for this item handler
		 * @param xPosition x coordinate
		 * @param yPosition y coordinate
		 * @return A Slot instance
		 */
		Slot getSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition);
	}
}
