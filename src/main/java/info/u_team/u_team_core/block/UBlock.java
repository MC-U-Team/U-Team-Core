package info.u_team.u_team_core.block;

import info.u_team.u_team_core.api.registry.IUBlock;
import net.minecraft.block.Block;
import net.minecraft.item.*;

public class UBlock extends Block implements IUBlock {
	
	protected final String name;
	
	protected ItemBlock itemblock;
	
	public UBlock(String name, Properties properties) {
		this(name, properties, true);
	}
	
	public UBlock(String name, Properties properties, boolean shouldCreateItemBlock) {
		this(name, null, properties, shouldCreateItemBlock);
	}
	
	public UBlock(String name, ItemGroup group, Properties properties) {
		this(name, group, properties, true);
	}
	
	public UBlock(String name, ItemGroup group, Properties properties, boolean shouldCreateItemBlock) {
		super(properties);
		this.name = name;
		if (shouldCreateItemBlock) {
			itemblock = createItemBlock(group);
		}
	}
	
	protected ItemBlock createItemBlock(ItemGroup group) {
		return new ItemBlock(this, group == null ? new Item.Properties() : new Item.Properties().group(group));
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	@Override
	public ItemBlock getItemBlock() {
		return itemblock;
	}
	
}
