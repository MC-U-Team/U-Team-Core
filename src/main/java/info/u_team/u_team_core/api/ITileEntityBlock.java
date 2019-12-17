package info.u_team.u_team_core.api;

import java.util.Optional;

import info.u_team.u_team_core.api.sync.IInitSyncedTileEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
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
	TileEntityType<?> getTileEntityType(IBlockReader world, BlockPos pos);
	
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
	default ActionResultType openContainer(World world, BlockPos pos, PlayerEntity player) {
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
	default ActionResultType openContainer(World world, BlockPos pos, PlayerEntity player, boolean canOpenSneak) {
		if (world.isRemote || !(player instanceof ServerPlayerEntity)) {
			return ActionResultType.SUCCESS;
		}
		
		final ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
		Optional<TileEntity> tileEntityOptional = isTileEntityFromType(world, pos);
		
		if (!tileEntityOptional.isPresent()) {
			return ActionResultType.PASS;
		}
		
		final TileEntity tileEntity = tileEntityOptional.get();
		
		if (!(tileEntity instanceof INamedContainerProvider)) {
			return ActionResultType.PASS;
		}
		
		if (!canOpenSneak && serverPlayer.func_225608_bj_()) {
			return ActionResultType.SUCCESS;
		}
		
		final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		if (tileEntity instanceof IInitSyncedTileEntity) {
			((IInitSyncedTileEntity) tileEntity).sendInitialDataBuffer(buffer);
		}
		
		NetworkHooks.openGui(serverPlayer, (INamedContainerProvider) tileEntity, extraData -> {
			extraData.writeBlockPos(pos);
			extraData.writeVarInt(buffer.readableBytes());
			extraData.writeBytes(buffer);
		});
		return ActionResultType.SUCCESS;
		
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
	default <T extends TileEntity> Optional<T> isTileEntityFromType(IBlockReader world, BlockPos pos) {
		final TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null || getTileEntityType(world, pos) != tileEntity.getType()) {
			return Optional.empty();
		}
		return Optional.of((T) tileEntity);
	}
}
