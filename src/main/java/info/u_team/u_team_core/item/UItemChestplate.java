package info.u_team.u_team_core.item;

import info.u_team.u_team_core.creativetab.UCreativeTab;

/**
 * Item API<br>
 * -> Basic Armor Item
 * 
 * @date 27.12.17
 * @author HyCraftHD
 *
 */
public class UItemChestplate extends UItemArmor {
	
	public UItemChestplate(ArmorMaterial material, String name, UCreativeTab tab) {
		super(material, 1, "chestplate", name, tab);
	}
	
}
