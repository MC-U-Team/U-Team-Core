package info.u_team.u_team_core.api.block;

import java.util.Optional;

import javax.annotation.Nullable;

import info.u_team.u_team_core.api.sync.MenuSyncedBlockEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

/**
 * Provides a convenience way to implement block entities for blocks.
 *
 * @author HyCraftHD
 */
public interface EntityBlockProvider extends EntityBlock {
	
	/**
	 * Returns the {@link BlockEntityType} that is used for creating the {@link BlockEntity} when
	 * {@link #newBlockEntity(BlockPos, BlockState)} is invoked. Can return null if for the current state or position no
	 * block entity should be created.
	 *
	 * @param pos Position of the block
	 * @param state Block state
	 */
	@Nullable
	BlockEntityType<?> blockEntityType(BlockPos pos, BlockState state);
	
	/**
	 * Returns a new {@link BlockEntity} for the give {@link BlockPos} and {@link BlockState}. <br>
	 * The default implementation creates a new {@link BlockEntity} by using the
	 * {@link #blockEntityType(BlockPos, BlockState)} method and invoke {@link BlockEntityType#create(BlockPos, BlockState)}
	 * Can return null if no block entity should be created for that position or state.
	 * 
	 * @param pos Position of the block
	 * @param state Block state
	 */
	@Nullable
	@Override
	default BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		final var type = blockEntityType(pos, state);
		if (type != null) {
			return type.create(pos, state);
		}
		return null;
	}
	
	/**
	 * Returns a optional with can contain the {@link BlockEntity} at that give position if the {@link BlockEntityType} is
	 * correct. Otherwise returns an empty optional.
	 * 
	 * @param <T> Block entity
	 * @param level Level
	 * @param pos Position of the block
	 * @return Optional with the block entity
	 */
	default <T extends BlockEntity> Optional<T> getBlockEntity(BlockGetter level, BlockPos pos) {
		return getMatchingBlockEntity(level, pos);
	}
	
	/**
	 * Opens the container that is specified in the block entity with {@link MenuConstructor}. If the tile entity implements
	 * {@link MenuSyncedBlockEntity} then the {@link MenuSyncedBlockEntity#sendInitialDataBuffer(PacketBuffer)} is called
	 * and the data will be send to the client. The container cannot be opened when secondary use is active.
	 *
	 * @param level Level
	 * @param pos The block entities position
	 * @param player The player that opens the block entity
	 * @return {@link InteractionResult} if the container could be opened
	 */
	default InteractionResult openContainer(Level world, BlockPos pos, Player player) {
		return openContainer(world, pos, player, false);
	}
	
	/**
	 * Opens the container that is specified in the block entity with {@link MenuConstructor}. If the tile entity implements
	 * {@link MenuSyncedBlockEntity} then the {@link MenuSyncedBlockEntity#sendInitialDataBuffer(PacketBuffer)} is called
	 * and the data will be send to the client.
	 *
	 * @param level Level
	 * @param pos The block entities position
	 * @param player The player that opens the block entity
	 * @param canOpenWhenSecondaryUse If the container can be opened when secondary use is active
	 * @return {@link InteractionResult} if the container could be opened
	 */
	default InteractionResult openContainer(Level level, BlockPos pos, Player player, boolean canOpenWhenSecondaryUse) {
		if (level.isClientSide() || !(player instanceof ServerPlayer)) {
			return InteractionResult.SUCCESS;
		}
		
		final var serverPlayer = (ServerPlayer) player;
		final var blockEntityOptional = getBlockEntity(level, pos);
		
		if (!blockEntityOptional.isPresent()) {
			return InteractionResult.PASS;
		}
		
		final var blockEntity = blockEntityOptional.get();
		
		if (!(blockEntity instanceof MenuProvider)) {
			return InteractionResult.PASS;
		}
		
		if (!canOpenWhenSecondaryUse && serverPlayer.isSecondaryUseActive()) {
			return InteractionResult.SUCCESS;
		}
		
		final var buffer = new FriendlyByteBuf(Unpooled.buffer());
		if (blockEntity instanceof MenuSyncedBlockEntity syncedBlockEntity) {
			syncedBlockEntity.sendInitialDataBuffer(buffer);
		}
		
		NetworkHooks.openGui(serverPlayer, (MenuProvider) blockEntity, extraData -> {
			extraData.writeBlockPos(pos);
			extraData.writeVarInt(buffer.readableBytes());
			extraData.writeBytes(buffer);
			buffer.release();
		});
		
		return InteractionResult.SUCCESS;
	}
	
	/**
	 * Private helper method to avoid exposing the unchecked cast. Tries to find the matching block entity for our type in
	 * the world at the give position.
	 * 
	 * @param <T> Block entity
	 * @param level Level
	 * @param pos Position of the block
	 * @return Optional with the block entity
	 */
	@SuppressWarnings("unchecked")
	private <T extends BlockEntity> Optional<T> getMatchingBlockEntity(BlockGetter level, BlockPos pos) {
		return (Optional<T>) level.getBlockEntity(pos, blockEntityType(pos, level.getBlockState(pos)));
	}
	
}
