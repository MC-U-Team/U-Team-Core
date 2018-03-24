package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UItemAxe extends CustomItemAxe {
	
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
		this(name, tab, material, material.getDamageVsEntity(), speed);
	}
	
	public UItemAxe(String name, ToolMaterial material, float damage, float speed) {
		this(name, null, material, damage, speed);
	}
	
	public UItemAxe(String name, UCreativeTab tab, ToolMaterial material, float damage, float speed) {
		super(material, damage, speed);
		
		setRegistryName(USub.getID(), name);
		setUnlocalizedName(USub.getID() + ":" + name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
	}
	
	private final void register() {
		GameRegistry.register(this);
	}
	
}
