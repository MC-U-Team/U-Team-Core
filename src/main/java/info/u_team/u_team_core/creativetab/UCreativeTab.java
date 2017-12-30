package info.u_team.u_team_core.creativetab;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.*;

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
		super(label);
	}
	
	public void setIcon(Block block) {
		setIcon(block, 0);
	}
	
	public void setIcon(Block block, int metadata) {
		setIcon(Item.getItemFromBlock(block), metadata);
	}
	
	public void setIcon(Item item) {
		setIcon(item, 0);
	}
	
	public void setIcon(Item item, int metadata) {
		this.item = item;
		this.metadata = metadata;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem() {
		if (item == null) {
			return Items.ACACIA_BOAT;
		}
		return item;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getIconItemDamage() {
		if (item == null) {
			return 0;
		}
		return metadata;
	}
	
}
