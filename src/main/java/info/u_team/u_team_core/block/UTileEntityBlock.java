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
	
	@Override
	public TileEntityType<?> getTileEntityType(IBlockReader world, BlockPos pos) {
		return tileEntityType.get();
	}
	
}
