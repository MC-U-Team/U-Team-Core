package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UItemPickaxe extends ItemPickaxe {
	
	public UItemPickaxe(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemPickaxe(String name, UCreativeTab tab, ToolMaterial material) {
		super(material);
		
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
