package info.u_team.u_team_core.creativetab;

import info.u_team.u_team_core.sub.USub;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.*;
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
		super(USub.getID() + ":" + label);
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
	public ItemStack getTabIconItem() {
		if (item == null) {
			return new ItemStack(Items.ACACIA_BOAT);
		}
		return new ItemStack(item, 1, metadata);
	}
	
}
