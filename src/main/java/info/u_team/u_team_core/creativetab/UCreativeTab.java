package info.u_team.u_team_core.creativetab;

import javax.annotation.Nonnull;

import info.u_team.u_team_core.intern.UCoreConstants;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.*;

/**
 * CreativeTab API<br>
 * -> Basic CreativeTab
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UCreativeTab extends CreativeTabs {
	
	private Item icon = null;
	
	public UCreativeTab(@Nonnull String label, @Nonnull Item item) {
		super(label);
		this.setIcon(item);
		item.setCreativeTab(this);
	}
	
	public UCreativeTab(@Nonnull String label, @Nonnull ItemStack itemstack) {
		this(label, itemstack.getItem());
	}
	
	public UCreativeTab(@Nonnull String label, @Nonnull Block block) {
		this(label, Item.getItemFromBlock(block));
		block.setCreativeTab(this);
	}
	
	public void setIcon(@Nonnull Item item) {
		if (item == null) {
			UCoreConstants.LOGGER.warn("Tried to use a nullpointer as icon in setIcon in UCreativeTab");
			return;
		}
		this.icon = item;
	}
	
	@Override
	public Item getTabIconItem() {
		if (this.icon == null)
			return Items.ACACIA_BOAT;
		return this.icon;
	}
	
}
