package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IModelProvider;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Spade
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */

public class UItemSpade extends ItemSpade implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItemSpade(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemSpade(String name, UCreativeTab tab, ToolMaterial material) {
		super(material);
		
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
