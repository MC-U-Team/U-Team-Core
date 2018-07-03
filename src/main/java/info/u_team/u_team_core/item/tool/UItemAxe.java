package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.*;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.fml.relauncher.*;

public class UItemAxe extends ItemAxe implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItemAxe(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemAxe(String name, ToolMaterial material, float speed) {
		this(name, null, material, speed);
	}
	
	public UItemAxe(String name, UCreativeTab tab, ToolMaterial material) {
		this(name, tab, material, -3.0F);
	}
	
	public UItemAxe(String name, UCreativeTab tab, ToolMaterial material, float speed) {
		this(name, tab, material, material.getAttackDamage(), speed);
	}
	
	public UItemAxe(String name, ToolMaterial material, float damage, float speed) {
		this(name, null, material, damage, speed);
	}
	
	public UItemAxe(String name, UCreativeTab tab, ToolMaterial material, float damage, float speed) {
		super(material, damage, speed);
		
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
