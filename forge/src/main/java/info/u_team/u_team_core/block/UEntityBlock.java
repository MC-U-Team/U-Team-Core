package info.u_team.u_team_core.block;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.block.EntityBlockProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class UEntityBlock extends UBlock implements EntityBlockProvider {
	
	protected final Supplier<? extends BlockEntityType<?>> blockEntityType;
	
	public UEntityBlock(Properties properties, Supplier<? extends BlockEntityType<?>> blockEntityType) {
		this(properties, null, blockEntityType);
	}
	
	public UEntityBlock(Properties properties, Item.Properties blockItemProperties, Supplier<? extends BlockEntityType<?>> blockEntityType) {
		super(properties, blockItemProperties);
		this.blockEntityType = Suppliers.memoize(blockEntityType::get);
	}
	
	@Override
	public BlockEntityType<?> blockEntityType(BlockPos pos, BlockState state) {
		return blockEntityType.get();
	}
	
}
