package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.sync.*;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * A synchronized tile entity container with custom packets an a tile entity that implements {@link ISyncedTileEntity}
 * 
 * @author HyCraftHD
 * @param <T> Tile entity
 */
@Deprecated
public abstract class USyncedTileEntityContainer<T extends TileEntity & ISyncedTileEntity> extends USyncedContainer {
	
	protected final PlayerInventory playerInventory;
	protected final T tileEntity;
	
	/**
	 * Only used if the tile entity is an instance of {@link IAutoSyncedTileEntity}.
	 */
	private PacketBuffer lastBuffer;
	
	/**
	 * This is the server constructor for the container. The {@link #init(boolean)} is called.
	 * 
	 * @param type Container type
	 * @param id Window id
	 * @param playerInventory Player inventory
	 * @param tileEntity Tile entity
	 */
	public USyncedTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, T tileEntity) {
		this(type, id, playerInventory, tileEntity, true);
	}
	
	/**
	 * This is the server constructor for the container.
	 * 
	 * @param type Container type
	 * @param id Window id
	 * @param playerInventory Player inventory
	 * @param tileEntity Tile entity
	 * @param init If the constructor should call {@link #init(boolean)}
	 */
	public USyncedTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, T tileEntity, boolean init) {
		super(type, id);
		this.playerInventory = playerInventory;
		this.tileEntity = tileEntity;
		if (init) {
			init(true);
		}
	}
	
	/**
	 * This is the client constructor for the container. It calls {@link #getClientTileEntity(PacketBuffer)} to get the tile
	 * entity. The {@link #init(boolean)} is called.
	 * 
	 * @param type Container type
	 * @param id Window id
	 * @param playerInventory Player inventory
	 * @param buffer Initial data (specified with
	 *        {@link NetworkHooks#openGui(ServerPlayerEntity, INamedContainerProvider, java.util.function.Consumer)})
	 */
	public USyncedTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		this(type, id, playerInventory, buffer, true);
	}
	
	/**
	 * This is the client constructor for the container. It calls {@link #getClientTileEntity(PacketBuffer)} to get the tile
	 * entity.
	 * 
	 * @param type Container type
	 * @param id Window id
	 * @param playerInventory Player inventory
	 * @param buffer Initial data (specified with
	 *        {@link NetworkHooks#openGui(ServerPlayerEntity, INamedContainerProvider, java.util.function.Consumer)})
	 * @param init If the constructor should call {@link #init(boolean)}
	 */
	public USyncedTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer, boolean init) {
		super(type, id, buffer);
		this.playerInventory = playerInventory;
		this.tileEntity = getClientTileEntity(buffer);
		tileEntity.handleInitialDataBuffer(new PacketBuffer(Unpooled.wrappedBuffer(buffer.readByteArray(32592)))); // 32600 bytes, but minus the tile entity pos which takes 8 bytes
		if (init) {
			init(false);
		}
	}
	
	/**
	 * This methods reads a position from the {@link PacketBuffer} and then tries to find a matching client tile entity.
	 * This method is only client sided. If the tile entity does not exist or does not implement {@link ISyncedTileEntity}
	 * an {@link IllegalStateException} is thrown.
	 * 
	 * @param buffer Packet buffer with the read index at a {@link BlockPos}
	 * @return A tile entity that implements {@link ISyncedTileEntity}
	 */
	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	private T getClientTileEntity(PacketBuffer buffer) {
		TileEntity tile = Minecraft.getInstance().world.getTileEntity(buffer.readBlockPos());
		if (tile == null || !(tile instanceof ISyncedTileEntity)) {
			throw new IllegalStateException("The client tile entity must be present.");
		}
		return (T) tile;
	}
	
	/**
	 * Is called after the server and client constructor. If you want to use your own fields in the init method, set the
	 * last constructor boolean to false and then call this method your self in all constructors.
	 *
	 * @param server True if its the server side false otherwise
	 */
	protected abstract void init(boolean server);
	
	/**
	 * On the server side just returns the inventory. On the client side this method returns a new instance of
	 * {@link Inventory} with the same size as the passed inventory.
	 * 
	 * @param server True if is the server side false otherwise
	 * @param inventory Inventory to decide on
	 * @return The passed inventory on the server side of a new one on the client side
	 */
	protected IInventory getInventoryOnDist(boolean server, IInventory inventory) {
		return server ? inventory : new Inventory(inventory.getSizeInventory());
	}
	
	/**
	 * Gets the tile entity
	 * 
	 * @return tile entity
	 */
	public T getTileEntity() {
		return tileEntity;
	}
	
	/**
	 * Is called when a new container listener is added to this container. We extend this method so it will send us the sync
	 * data when we open the container if the tile entity implements {@link IAutoSyncedTileEntity}.
	 */
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (tileEntity instanceof IAutoSyncedTileEntity) {
			if (listener instanceof ServerPlayerEntity) {
				if (lastBuffer == null) {
					lastBuffer = new PacketBuffer(Unpooled.buffer());
					sendToClient(lastBuffer);
				}
				sendDataToClient((ServerPlayerEntity) listener, new PacketBuffer(lastBuffer.copy()));
			}
		}
	}
	
	/**
	 * This method is called every tick and when changes take place. This method will send to all players that have this
	 * container open the sync data if the buffer has changed and if the tile entity implements
	 * {@link IAutoSyncedTileEntity}.
	 */
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (tileEntity instanceof IAutoSyncedTileEntity) {
			final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
			sendToClient(buffer);
			if (buffer.equals(lastBuffer)) {
				return;
			}
			lastBuffer = buffer;
			listeners.stream().filter(listener -> listener instanceof ServerPlayerEntity).map(listener -> (ServerPlayerEntity) listener).forEach(player -> sendDataToClient(player, new PacketBuffer(lastBuffer.copy())));
		}
	}
	
	/**
	 * Calls the method on the tile entity. <br>
	 * <br>
	 * {@inheritDoc}
	 */
	@Override
	public void sendToClient(PacketBuffer buffer) {
		tileEntity.sendToClient(buffer);
	}
	
	/**
	 * Calls the method on the tile entity. <br>
	 * <br>
	 * {@inheritDoc}
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleFromServer(PacketBuffer buffer) {
		tileEntity.handleFromServer(buffer);
	}
	
	/**
	 * Calls the method on the tile entity. <br>
	 * <br>
	 * {@inheritDoc}
	 */
	@OnlyIn(Dist.CLIENT)
	@Override
	public void sendToServer(PacketBuffer buffer) {
		tileEntity.sendToServer(buffer);
	}
	
	/**
	 * Calls the method on the tile entity. <br>
	 * <br>
	 * {@inheritDoc}
	 */
	@Override
	public void handleFromClient(PacketBuffer buffer) {
		tileEntity.handleFromClient(buffer);
	}
	
}
