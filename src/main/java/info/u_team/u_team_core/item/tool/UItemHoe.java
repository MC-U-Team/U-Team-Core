package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.*;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.fml.relauncher.*;

public class UItemHoe extends ItemHoe implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItemHoe(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemHoe(String name, UCreativeTab tab, ToolMaterial material) {
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
