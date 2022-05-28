package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.block.MenuSyncedBlockEntity;
import info.u_team.u_team_core.util.CastUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkHooks;

public abstract class UBlockEntityContainerMenu<T extends BlockEntity> extends UContainerMenu {
	
	protected final Inventory playerInventory;
	protected final T blockEntity;
	
	/**
	 * This is the server constructor. The method {@link #init(LogicalSide)} is called.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 * @param playerInventory Player inventory
	 * @param blockEntity Block entity
	 */
	public UBlockEntityContainerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, T blockEntity) {
		this(menuType, containerId, playerInventory, blockEntity, true);
	}
	
	/**
	 * This is the server constructor.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 * @param playerInventory Player inventory
	 * @param blockEntity Block entity
	 * @param callInit If the constructor should call {@link #init(LogicalSide)}
	 */
	public UBlockEntityContainerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, T blockEntity, boolean callInit) {
		super(menuType, containerId);
		this.playerInventory = playerInventory;
		this.blockEntity = blockEntity;
		if (callInit) {
			init(LogicalSide.SERVER);
		}
	}
	
	/**
	 * This is the client constructor. This methods reads the block entity pos from the supplied {@link FriendlyByteBuf} and
	 * tries to get the block entity at the client side. The block pos must be the first entry in the buffer! The method
	 * {@link #init(LogicalSide)} is called.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 * @param playerInventory Player inventory
	 * @param byteBuf Initial menu data (specified with {@link NetworkHooks#openGui(net.minecraft.server.level.ServerPlayer,
	 *        net.minecraft.world.MenuProvider, java.util.function.Consumer))
	 */
	public UBlockEntityContainerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, FriendlyByteBuf byteBuf) {
		this(menuType, containerId, playerInventory, byteBuf, true);
	}
	
	/**
	 * This is the client constructor. This methods reads the block entity pos from the supplied {@link FriendlyByteBuf} and
	 * tries to get the block entity at the client side. The block pos must be the first entry in the buffer!
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 * @param playerInventory Player inventory
	 * @param byteBuf Initial menu data (specified with
	 *        {@link NetworkHooks#openGui(net.minecraft.server.level.ServerPlayer, net.minecraft.world.MenuProvider, java.util.function.Consumer)})
	 * @param callInit If the constructor should call {@link #init(LogicalSide)}
	 */
	public UBlockEntityContainerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, FriendlyByteBuf byteBuf, boolean callInit) {
		super(menuType, containerId);
		this.playerInventory = playerInventory;
		blockEntity = getClientTileEntity(byteBuf);
		if (blockEntity instanceof final MenuSyncedBlockEntity syncedBlockEntity) {
			final FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.wrappedBuffer(byteBuf.readByteArray(32592))); // 32600 bytes, but minus the tile entity pos which takes 8 bytes
			syncedBlockEntity.handleInitialMenuDataFromServer(data);
			data.release();
		}
		if (callInit) {
			init(LogicalSide.CLIENT);
		}
	}
	
	/**
	 * This methods reads the {@link BlockPos} from the {@link FriendlyByteBuf} and then tries to find a client block
	 * entity. This method is only client sided. If the tile entity does not exist an {@link IllegalStateException} is
	 * thrown.
	 *
	 * @param byteBuf Buffer with the read index at the block entity {@link BlockPos}
	 * @return The block entity on the clients side
	 */
	@OnlyIn(Dist.CLIENT)
	private T getClientTileEntity(FriendlyByteBuf byteBuf) {
		final BlockPos pos = byteBuf.readBlockPos();
		final BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
		if (blockEntity == null) {
			throw new IllegalStateException("The client block entity at (" + pos.toShortString() + ") does not exists.");
		}
		return CastUtil.uncheckedCast(blockEntity);
	}
	
	/**
	 * Is called after the server and client constructor. If you want to use your own fields in the init method, set the
	 * last constructor boolean to false and then call this method in all constructors of the implementing class.
	 *
	 * @param side Logical side this method is called on
	 */
	protected abstract void init(LogicalSide side);
	
	/**
	 * Gets the block entity
	 *
	 * @return Block entity associated with this menu
	 */
	public T getBlockEntity() {
		return blockEntity;
	}
	
}
