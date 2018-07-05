package info.u_team.u_team_core.item;

import info.u_team.u_team_core.api.IModelProvider;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Item
 * 
 * @date 17.08.2017
 * @author MrTroble
 */

public class UItem extends Item implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItem(String name) {
		this(name, null);
	}
	
	public UItem(String name, UCreativeTab tab) {
		super();
		
		this.name = name;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		setModel(this, 0, getRegistryName());
	}
	
}
