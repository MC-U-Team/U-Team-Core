package info.u_team.u_team_core.block;

import info.u_team.u_team_core.api.registry.IUBlock;
import net.minecraft.block.Block;
import net.minecraft.item.*;

public class UBlock extends Block implements IUBlock {
	
	protected final String name;
	
	protected final ItemBlock itemblock;
	
	public UBlock(String name, Properties properties) {
		super(properties);
		this.name = name;
		this.itemblock = new ItemBlock(this, new Item.Properties());
	}
	
	public UBlock(String name, Properties properties, ItemBlock itemblock) {
		super(properties);
		this.name = name;
		this.itemblock = itemblock;
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
