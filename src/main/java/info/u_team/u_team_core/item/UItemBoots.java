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
public class UItemBoots extends UItemArmor {
	
	public UItemBoots(ArmorMaterial material, String name, UCreativeTab tab) {
		super(material, 3, "boots", name, tab);
	}
	
}
