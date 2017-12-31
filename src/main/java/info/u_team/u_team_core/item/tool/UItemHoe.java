package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UItemHoe extends ItemHoe {
	
	public UItemHoe(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemHoe(String name, UCreativeTab tab, ToolMaterial material) {
		super(material);
		
		setRegistryName(USub.getID(), name);
		setUnlocalizedName(name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
	}
	
	private final void register() {
		GameRegistry.register(this);
	}
}
