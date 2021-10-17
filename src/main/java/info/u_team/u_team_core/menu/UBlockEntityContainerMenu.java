package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.sync.MenuSyncedBlockEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public abstract class UBlockEntityContainerMenu<T extends BlockEntity> extends UContainerMenu {
	
	protected final Inventory playerInventory;
	protected final T tileEntity;
	
	/**
	 * This is the server constructor for the container. The {@link #init(boolean)} is called.
	 *
	 * @param type Container type
	 * @param id Window id
	 * @param playerInventory Player inventory
	 * @param tileEntity Tile entity
	 */
	public UBlockEntityContainerMenu(MenuType<?> type, int id, Inventory playerInventory, T tileEntity) {
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
	public UBlockEntityContainerMenu(MenuType<?> type, int id, Inventory playerInventory, T tileEntity, boolean init) {
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
	 *        {@link NetworkHooks#openGui(net.minecraft.entity.player.ServerPlayerEntity, net.minecraft.inventory.container.INamedContainerProvider, java.util.function.Consumer)})
	 */
	public UBlockEntityContainerMenu(MenuType<?> type, int id, Inventory playerInventory, FriendlyByteBuf buffer) {
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
	 *        {@link NetworkHooks#openGui(net.minecraft.entity.player.ServerPlayerEntity, net.minecraft.inventory.container.INamedContainerProvider, java.util.function.Consumer)})
	 * @param init If the constructor should call {@link #init(boolean)}
	 */
	public UBlockEntityContainerMenu(MenuType<?> type, int id, Inventory playerInventory, FriendlyByteBuf buffer, boolean init) {
		super(type, id);
		this.playerInventory = playerInventory;
		this.tileEntity = getClientTileEntity(buffer);
		if (tileEntity instanceof MenuSyncedBlockEntity) {
			((MenuSyncedBlockEntity) tileEntity).handleInitialDataBuffer(new FriendlyByteBuf(Unpooled.wrappedBuffer(buffer.readByteArray(32592)))); // 32600 bytes, but minus the tile entity pos which takes 8 bytes
		}
		if (init) {
			init(false);
		}
	}
	
	/**
	 * This methods reads a position from the {@link PacketBuffer} and then tries to find a matching client tile entity.
	 * This method is only client sided. If the tile entity does not exist an {@link IllegalStateException} is thrown.
	 *
	 * @param buffer Packet buffer with the read index at a {@link BlockPos}
	 * @return A tile entity that implements {@link ISyncedTileEntity}
	 */
	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	private T getClientTileEntity(FriendlyByteBuf buffer) {
		final var tile = Minecraft.getInstance().level.getBlockEntity(buffer.readBlockPos());
		if (tile == null) {
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
	 * Gets the tile entity
	 *
	 * @return tile entity
	 */
	public T getTileEntity() {
		return tileEntity;
	}
	
}
