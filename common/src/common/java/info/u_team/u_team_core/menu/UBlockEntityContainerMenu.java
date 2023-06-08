package info.u_team.u_team_core.menu;

import info.u_team.u_team_core.api.Platform.Environment;
import info.u_team.u_team_core.api.block.MenuSyncedBlockEntity;
import info.u_team.u_team_core.api.network.NetworkEnvironment;
import info.u_team.u_team_core.util.CastUtil;
import info.u_team.u_team_core.util.EnvironmentUtil;
import info.u_team.u_team_core.util.MenuUtil;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class UBlockEntityContainerMenu<T extends BlockEntity> extends UContainerMenu {
	
	protected final Inventory playerInventory;
	protected final T blockEntity;
	
	/**
	 * This is the server constructor. The method {@link #init(NetworkEnvironment)} is called.
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
	 * @param callInit If the constructor should call {@link #init(NetworkEnvironment)}
	 */
	public UBlockEntityContainerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, T blockEntity, boolean callInit) {
		super(menuType, containerId);
		this.playerInventory = playerInventory;
		this.blockEntity = blockEntity;
		if (callInit) {
			init(NetworkEnvironment.SERVER);
		}
	}
	
	/**
	 * This is the client constructor. This methods reads the block entity pos from the supplied {@link FriendlyByteBuf} and
	 * tries to get the block entity at the client side. The block pos must be the first entry in the buffer! The method
	 * {@link #init(NetworkEnvironment)} is called.
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 * @param playerInventory Player inventory
	 * @param buffer Initial menu data (specified with
	 *        {@link MenuUtil#openMenu(net.minecraft.server.level.ServerPlayer, net.minecraft.world.MenuProvider, java.util.function.Consumer, boolean)})
	 */
	public UBlockEntityContainerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
		this(menuType, containerId, playerInventory, buffer, true);
	}
	
	/**
	 * This is the client constructor. This methods reads the block entity pos from the supplied {@link FriendlyByteBuf} and
	 * tries to get the block entity at the client side. The block pos must be the first entry in the buffer!
	 *
	 * @param menuType Menu type
	 * @param containerId Container id
	 * @param playerInventory Player inventory
	 * @param buffer Initial menu data (specified with
	 *        {@link MenuUtil#openMenu(net.minecraft.server.level.ServerPlayer, net.minecraft.world.MenuProvider, java.util.function.Consumer, boolean)})
	 * @param callInit If the constructor should call {@link #init(NetworkEnvironment)}
	 */
	public UBlockEntityContainerMenu(MenuType<?> menuType, int containerId, Inventory playerInventory, FriendlyByteBuf buffer, boolean callInit) {
		super(menuType, containerId);
		this.playerInventory = playerInventory;
		blockEntity = EnvironmentUtil.callWhen(Environment.CLIENT, () -> () -> Client.getClientBlockEntity(buffer));
		if (blockEntity instanceof final MenuSyncedBlockEntity syncedBlockEntity) {
			final FriendlyByteBuf data = new FriendlyByteBuf(Unpooled.wrappedBuffer(buffer.readByteArray(32592))); // 32600 bytes, but minus the block entity pos which takes 8 bytes
			syncedBlockEntity.handleInitialMenuDataFromServer(data);
			data.release();
		}
		if (callInit) {
			init(NetworkEnvironment.CLIENT);
		}
	}
	
	/**
	 * Is called after the server and client constructor. If you want to use your own fields in the init method, set the
	 * last constructor boolean to false and then call this method in all constructors of the implementing class.
	 *
	 * @param environment Logical side this method is called on
	 */
	protected abstract void init(NetworkEnvironment environment);
	
	/**
	 * Gets the block entity
	 *
	 * @return Block entity associated with this menu
	 */
	public T getBlockEntity() {
		return blockEntity;
	}
	
	private static class Client {
		
		/**
		 * This methods reads the {@link BlockPos} from the {@link FriendlyByteBuf} and then tries to find a client block
		 * entity. This method is only client sided. If the block entity does not exist an {@link IllegalStateException} is
		 * thrown.
		 *
		 * @param buffer Buffer with the read index at the block entity {@link BlockPos}
		 * @return The block entity on the clients side
		 */
		private static <T extends BlockEntity> T getClientBlockEntity(FriendlyByteBuf buffer) {
			final BlockPos pos = buffer.readBlockPos();
			final BlockEntity blockEntity = Minecraft.getInstance().level.getBlockEntity(pos);
			if (blockEntity == null) {
				throw new IllegalStateException("The client block entity at (" + pos.toShortString() + ") does not exist.");
			}
			return CastUtil.uncheckedCast(blockEntity);
		}
	}
}
