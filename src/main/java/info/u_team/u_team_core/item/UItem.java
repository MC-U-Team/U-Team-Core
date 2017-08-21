package info.u_team.u_team_core.item;

import javax.annotation.*;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.Item;

/**
 * Item API<br>
 * -> Basic Item
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UItem extends Item {
	
	public UItem(@Nonnull String name, @Nullable UCreativeTab tab) {
		this.setUnlocalizedName(name);
		this.setRegistryName(UCoreMain.SUBMOD_MODID, name);
		if (tab != null) {
			this.setCreativeTab(tab);
		}
	}
	
}
