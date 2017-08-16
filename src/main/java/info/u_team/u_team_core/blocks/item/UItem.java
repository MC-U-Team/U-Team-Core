package info.u_team.u_team_core.blocks.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.Item;

/**
 * Item API<br>
 *   -> Basic Item
 * 
 * @author MrTroble
 *
 */

public class UItem extends Item{

	public UItem(@Nonnull String name, @Nullable UCreativeTab tab) {
		this.setUnlocalizedName(name);
		this.setRegistryName(UCoreMain.SUBMOD_MODID, name);
		if(tab != null)this.setCreativeTab(tab);
	}
	
}
