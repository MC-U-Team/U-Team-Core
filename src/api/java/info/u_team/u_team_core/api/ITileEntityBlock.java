package info.u_team.u_team_core.api;

import java.util.Optional;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * Implement this in your tile entity block when you want utility functions for opening guis with your tile entity.
 * 
 * @author HyCraftHD
 */
public interface ITileEntityBlock {
	
	/**
	 * Returns the {@link TileEntityType} of the block.
	 * 
	 * @param world World
	 * @param pos The tile entities position
	 * @return The tile entity type
	 */
	BlockEntityType<?> getTileEntityType(BlockGetter world, BlockPos pos);
	
	/**
	 * Opens the container that is specified in the tile entity with {@link IContainerProvider}. If the tile entity
	 * implements {@link IInitSyncedTileEntity} then the {@link IInitSyncedTileEntity#sendInitialDataBuffer(PacketBuffer)}
	 * is called and the data will be send to the client. The container cannot be opened when sneaking.
	 * 
	 * @param world World
	 * @param pos The tile entities position
	 * @param player The player that opens the tile entity
	 * @return If the container could be opened
	 */
	default InteractionResult openContainer(Level world, BlockPos pos, Player player) {
		return openContainer(world, pos, player, false);
	}
	
	/**
	 * Opens the container that is specified in the tile entity with {@link IContainerProvider}. If the tile entity
	 * implements {@link IInitSyncedTileEntity} then the {@link IInitSyncedTileEntity#sendInitialDataBuffer(PacketBuffer)}
	 * is called and the data will be send to the client.
	 * 
	 * @param world World
	 * @param pos The tile entities pos
	 * @param player The player that opens the tile entity
	 * @param canOpenSneak If the container can be opened when shift clicking with an item that has no right click function
	 * @return If the container could be opened
	 */
	default InteractionResult openContainer(Level world, BlockPos pos, Player player, boolean canOpenSneak) {
		if (world.isClientSide || !(player instanceof ServerPlayer)) {
			return InteractionResult.SUCCESS;
		}
		
		final ServerPlayer serverPlayer = (ServerPlayer) player;
		final Optional<BlockEntity> tileEntityOptional = isTileEntityFromType(world, pos);
		
		if (!tileEntityOptional.isPresent()) {
			return InteractionResult.PASS;
		}
		
		final BlockEntity tileEntity = tileEntityOptional.get();
		
		if (!(tileEntity instanceof MenuProvider)) {
			return InteractionResult.PASS;
		}
		
		if (!canOpenSneak && serverPlayer.isShiftKeyDown()) {
			return InteractionResult.SUCCESS;
		}
		
		final FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
		if (tileEntity instanceof IInitSyncedTileEntity) {
			((IInitSyncedTileEntity) tileEntity).sendInitialDataBuffer(buffer);
		}
		
		NetworkHooks.openGui(serverPlayer, (MenuProvider) tileEntity, extraData -> {
			extraData.writeBlockPos(pos);
			extraData.writeVarInt(buffer.readableBytes());
			extraData.writeBytes(buffer);
		});
		return InteractionResult.SUCCESS;
		
	}
	
	/**
	 * Return an optional with a tile entity if the tile entity at this position exists and is the same tile entity type as
	 * this block creates. This method is unchecked with a generic attribute.
	 * 
	 * @param <T> Tile entity
	 * @param world World
	 * @param pos Position of the tile entity
	 * @return Optional with tile entity or empty
	 */
	@SuppressWarnings("unchecked")
	default <T extends BlockEntity> Optional<T> isTileEntityFromType(BlockGetter world, BlockPos pos) {
		final BlockEntity tileEntity = world.getBlockEntity(pos);
		if (tileEntity == null || getTileEntityType(world, pos) != tileEntity.getType()) {
			return Optional.empty();
		}
		return Optional.of((T) tileEntity);
	}
}
