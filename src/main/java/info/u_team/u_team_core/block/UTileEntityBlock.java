package info.u_team.u_team_core.block;

import java.util.Optional;
import java.util.function.Supplier;

import info.u_team.u_team_core.api.ISyncedTileEntity;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.NetworkHooks;

public class UTileEntityBlock extends UBlock {
	
	protected final Supplier<TileEntityType<?>> tileEntityType;
	
	public UTileEntityBlock(String name, Properties properties, Supplier<TileEntityType<?>> tileentitytype) {
		this(name, null, properties, tileentitytype);
	}
	
	public UTileEntityBlock(String name, ItemGroup group, Properties properties, Supplier<TileEntityType<?>> tileentitytype) {
		this(name, group, properties, null, tileentitytype);
	}
	
	public UTileEntityBlock(String name, Properties properties, Item.Properties itemblockproperties, Supplier<TileEntityType<?>> tileentitytype) {
		this(name, null, properties, itemblockproperties, tileentitytype);
	}
	
	public UTileEntityBlock(String name, ItemGroup group, Properties properties, Item.Properties itemblockproperties, Supplier<TileEntityType<?>> tileentitytype) {
		super(name, group, properties, itemblockproperties);
		this.tileEntityType = tileentitytype;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return tileEntityType.get().create();
	}
	
	public boolean openContainer(World world, BlockPos pos, PlayerEntity player) {
		return openContainer(world, pos, player, false);
	}
	
	public boolean openContainer(World world, BlockPos pos, PlayerEntity player, boolean canOpenSneak) {
		if (world.isRemote || !(player instanceof ServerPlayerEntity)) {
			return true;
		}
		
		final ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
		Optional<TileEntity> tileEntityOptional = isTileEntityFromType(world, pos);
		
		if (!tileEntityOptional.isPresent()) {
			return false;
		}
		
		final TileEntity tileEntity = tileEntityOptional.get();
		
		if (!(tileEntity instanceof INamedContainerProvider)) {
			return false;
		}
		
		if (!canOpenSneak && serverPlayer.isSneaking()) {
			return true;
		}
		
		final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		if (tileEntity instanceof ISyncedTileEntity) {
			((ISyncedTileEntity) tileEntity).sendInitialDataBuffer(buffer);
		}
		
		NetworkHooks.openGui(serverPlayer, (INamedContainerProvider) tileEntity, buf -> {
			buf.writeBlockPos(pos);
			buf.writeBytes(buffer);
		});
		return true;
		
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
	public <T extends TileEntity> Optional<T> isTileEntityFromType(IBlockReader world, BlockPos pos) {
		final TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null || tileEntityType.get() != tileEntity.getType()) {
			return Optional.empty();
		}
		return Optional.of((T) tileEntity);
	}
	
}
