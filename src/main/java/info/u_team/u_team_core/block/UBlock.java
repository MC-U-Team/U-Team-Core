package info.u_team.u_team_core.block;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;

import info.u_team.u_team_core.api.block.BlockItemProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class UBlock extends Block implements BlockItemProvider {
	
	protected final Supplier<Item> blockItem;
	
	public UBlock(Properties properties) {
		this(properties, null);
	}
	
	public UBlock(Properties properties, Item.Properties blockItemProperties) {
		super(properties);
		blockItem = Suppliers.memoize(() -> createBlockItem(blockItemProperties == null ? new Item.Properties() : blockItemProperties));
	}
	
	protected Item createBlockItem(Item.Properties blockItemProperties) {
		return new BlockItem(this, blockItemProperties);
	}
	
	@Override
	public Item blockItem() {
		return blockItem.get();
	}
}
