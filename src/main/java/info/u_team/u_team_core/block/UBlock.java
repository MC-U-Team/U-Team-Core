package info.u_team.u_team_core.block;

import info.u_team.u_team_core.api.registry.BlockItemProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class UBlock extends Block implements BlockItemProvider {
	
	protected final Item blockItem;
	
	public UBlock(Properties properties) {
		this(null, properties);
	}
	
	public UBlock(CreativeModeTab creativeTab, Properties properties) {
		this(creativeTab, properties, null);
	}
	
	public UBlock(Properties properties, Item.Properties blockItemProperties) {
		this(null, properties, blockItemProperties);
	}
	
	public UBlock(CreativeModeTab creativeTab, Properties properties, Item.Properties blockItemProperties) {
		super(properties);
		blockItem = createBlockItem(blockItemProperties == null ? new Item.Properties().tab(creativeTab) : creativeTab == null ? blockItemProperties : blockItemProperties.tab(creativeTab));
	}
	
	protected Item createBlockItem(Item.Properties blockItemProperties) {
		return new BlockItem(this, blockItemProperties);
	}
	
	@Override
	public Item getBlockItem() {
		return blockItem;
	}
}
