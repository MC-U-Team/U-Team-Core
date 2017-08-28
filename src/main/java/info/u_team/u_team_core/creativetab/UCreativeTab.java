package info.u_team.u_team_core.creativetab;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * CreativeTab API<br>
 * -> Basic CreativeTab
 * 
 * @author MrTroble, HyCraftHD
 * @date 17.08.2017
 */

public class UCreativeTab extends CreativeTabs {
	
	private Item item = null;
	private int metadata = 0;
	
	public UCreativeTab(String label) {
		this(label, (Item)null, 0, false);
	}
	
	public UCreativeTab(String label, Item item) {
		this(label, item, 0, false);
	}
	
	public UCreativeTab(String label, Item item, int metadata) {
		this(label, item, metadata, false);
	}
	
	public UCreativeTab(String label, Item item, boolean settab) {
		this(label, item, 0, settab);
	}
	
	public UCreativeTab(String label, Block block, int metadata, boolean settab) {
		this(label, Item.getItemFromBlock(block), metadata, settab);
	}
	
	public UCreativeTab(String label, Block block) {
		this(label, block, 0, false);
	}
	
	public UCreativeTab(String label, Block block, int metadata) {
		this(label, block, metadata, false);
	}
	
	public UCreativeTab(String label, Block block, boolean settab) {
		this(label, block, 0, settab);
	}
	
	public UCreativeTab(String label, Item item, int metadata, boolean settab) {
		super(label);
		this.item = item;
		this.metadata = metadata;
		
		if (settab && item != null) {
			item.setCreativeTab(this);
		}
	}
	
	public void setIcon(Item item, int metadata) {
		this.item = item;
		this.metadata = metadata;
	}
	
	@Override
	public Item getTabIconItem() {
		if (item == null) {
			return Items.ACACIA_BOAT;
		}
		return item;
	}
	
	@Override
	public int getIconItemDamage() {
		if (item == null) {
			return 0;
		}
		return metadata;
	}
	
}
