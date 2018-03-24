package info.u_team.u_team_core.item;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Item API<br>
 * -> Basic Item
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UItem extends Item {
	
	public UItem(String name) {
		this(name, null);
	}
	
	public UItem(String name, UCreativeTab tab) {
		super();
		
		setRegistryName(USub.getID(), name);
		setUnlocalizedName(USub.getID() + ":" + name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
	}
	
	private final void register() {
		GameRegistry.registerItem(this);
	}
	
}
