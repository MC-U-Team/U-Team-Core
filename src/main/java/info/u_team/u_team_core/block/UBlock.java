package info.u_team.u_team_core.block;

import info.u_team.u_team_core.api.registry.IUBlock;
import net.minecraft.block.Block;
import net.minecraft.item.*;

public class UBlock extends Block implements IUBlock {
	
	protected final String name;
	
	protected final ItemBlock itemblock;
	
	public UBlock(String name, Properties properties) {
		this(name, null, properties);
	}
	
	public UBlock(String name, ItemGroup group, Properties properties) {
		super(properties);
		this.name = name;
		itemblock = createItemBlock(group);
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
