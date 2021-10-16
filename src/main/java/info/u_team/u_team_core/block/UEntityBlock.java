package info.u_team.u_team_core.block;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.block.EntityBlockProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class UEntityBlock extends UBlock implements EntityBlockProvider {
	
	protected final Supplier<? extends BlockEntityType<?>> tileEntityType;
	
	public UEntityBlock(Properties properties, Supplier<? extends BlockEntityType<?>> tileEntityType) {
		this(null, properties, tileEntityType);
	}
	
	public UEntityBlock(CreativeModeTab creativeTab, Properties properties, Supplier<? extends BlockEntityType<?>> tileEntityType) {
		this(creativeTab, properties, null, tileEntityType);
	}
	
	public UEntityBlock(Properties properties, Item.Properties blockItemProperties, Supplier<? extends BlockEntityType<?>> tileEntityType) {
		this(null, properties, blockItemProperties, tileEntityType);
	}
	
	public UEntityBlock(CreativeModeTab creativeTab, Properties properties, Item.Properties blockItemProperties, Supplier<? extends BlockEntityType<?>> tileEntityType) {
		super(creativeTab, properties, blockItemProperties);
		this.tileEntityType = Suppliers.memoize(tileEntityType::get);
	}
	
	@Override
	public BlockEntityType<?> blockEntityType(BlockPos pos, BlockState state) {
		return tileEntityType.get();
	}
	
}
