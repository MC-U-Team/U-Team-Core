package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IModelProvider;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Pickaxe
 * 
 * @date 05.07.2018
 * @author HyCraftHD
 */

public class UItemPickaxe extends ItemPickaxe implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItemPickaxe(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemPickaxe(String name, UCreativeTab tab, ToolMaterial material) {
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
