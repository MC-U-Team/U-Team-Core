package info.u_team.u_team_core.container;

import info.u_team.u_team_core.api.ISyncedContainerTileEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * A synchronized tile entity container with custom packets an a tile entity that implements
 * {@link ISyncedContainerTileEntity}
 * 
 * @author HyCraftHD
 *
 * @param <T> Tile entity
 */
public abstract class USyncedTileEntityContainer<T extends TileEntity & ISyncedContainerTileEntity> extends USyncedContainer {
	
	protected final PlayerInventory playerInventory;
	protected final T tileEntity;
	
	/**
	 * This is the server constructor for the container. The {@link #init()} is called.
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
	 * @param init If the constructor should call {@link #init()}
	 */
	public USyncedTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, T tileEntity, boolean init) {
		super(type, id);
		this.playerInventory = playerInventory;
		this.tileEntity = tileEntity;
		if (init) {
			init();
		}
	}
	
	/**
	 * This is the client constructor for the container. It calls {@link #getClientTileEntity(PacketBuffer)} to get the tile
	 * entity. The {@link #init()} is called.
	 * 
	 * @param type Container type
	 * @param id Window id
	 * @param playerInventory Player inventory
	 * @param buffer Initial data (specified with {@link NetworkHooks#openGui(player, containerSupplier,extraDataWriter)})
	 */
	@OnlyIn(Dist.CLIENT)
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
	 * @param buffer Initial data (specified with {@link NetworkHooks#openGui(player, containerSupplier,extraDataWriter)})
	 * @param init If the constructor should call {@link #init()}
	 */
	@OnlyIn(Dist.CLIENT)
	public USyncedTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer, boolean init) {
		super(type, id, buffer);
		this.playerInventory = playerInventory;
		this.tileEntity = getClientTileEntity(buffer);
		tileEntity.handleInitialDataBuffer(buffer);
		if (init) {
			init();
		}
	}
	
	/**
	 * This methods reads a position from the {@link PacketBuffer} and then tries to find a matching client tile entity.
	 * This method is only client sided. If the tile entity does not exist or does not implement
	 * {@link ISyncedContainerTileEntity} an {@link IllegalStateException} is thrown.
	 * 
	 * @param buffer Packet buffer with the read index at a {@link BlockPos}
	 * @return A tile entity that implements {@link ISyncedContainerTileEntity}
	 */
	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	private T getClientTileEntity(PacketBuffer buffer) {
		TileEntity tile = Minecraft.getInstance().world.getTileEntity(buffer.readBlockPos());
		if (tile == null || !(tile instanceof ISyncedContainerTileEntity)) {
			throw new IllegalStateException("The client tile entity must be present.");
		}
		return (T) tile;
	}
	
	/**
	 * Is called after the server and client constructor. If you want to use your own fields in the init method, set the
	 * last constructor boolean to false and then call this method your self in all constructors.
	 */
	protected abstract void init();
	
	/**
	 * Gets the tile entity
	 * 
	 * @return tile entity
	 */
	public T getTileEntity() {
		return tileEntity;
	}
	
	// Testing
	
	private PacketBuffer lastBuffer;
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (listener instanceof ServerPlayerEntity) {
			if (lastBuffer == null) {
				lastBuffer = new PacketBuffer(Unpooled.buffer());
				sendToClient(lastBuffer);
				sendDataToClient((ServerPlayerEntity) listener, lastBuffer);
			}
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		sendToClient(buffer);
		if (buffer.equals(lastBuffer)) {
			return;
		}
		lastBuffer = buffer;
		listeners.stream().filter(listener -> listener instanceof ServerPlayerEntity).map(listener -> (ServerPlayerEntity) listener).forEach(player -> {
			sendDataToClient(player, buffer);
		});
	}

	
	@Override
	public void sendToClient(PacketBuffer buffer) {
		tileEntity.sendToClient(buffer);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleFromServer(PacketBuffer buffer) {
		tileEntity.handleFromServer(buffer);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void sendToServer(PacketBuffer buffer) {
		tileEntity.sendToServer(buffer);
	}
	
	@Override
	public void handleFromClient(PacketBuffer buffer) {
		tileEntity.handleFromClient(buffer);
	}
	
}
