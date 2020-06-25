package info.u_team.u_team_core.block;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.ITileEntityBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class UTileEntityBlock extends UBlock implements ITileEntityBlock {
	
	protected final Supplier<TileEntityType<?>> tileEntityType;
	
	public UTileEntityBlock(Properties properties, Supplier<TileEntityType<?>> tileEntityType) {
		this(null, properties, tileEntityType);
	}
	
	public UTileEntityBlock(ItemGroup group, Properties properties, Supplier<TileEntityType<?>> tileEntityType) {
		this(group, properties, null, tileEntityType);
	}
	
	public UTileEntityBlock(Properties properties, Item.Properties itemblockproperties, Supplier<TileEntityType<?>> tileEntityType) {
		this(null, properties, itemblockproperties, tileEntityType);
	}
	
	public UTileEntityBlock(ItemGroup group, Properties properties, Item.Properties itemblockproperties, Supplier<TileEntityType<?>> tileEntityType) {
		super(group, properties, itemblockproperties);
		this.tileEntityType = tileEntityType;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return tileEntityType.get().create();
	}
	
	@Override
	public TileEntityType<?> getTileEntityType(IBlockReader world, BlockPos pos) {
		return tileEntityType.get();
	}
	
}
