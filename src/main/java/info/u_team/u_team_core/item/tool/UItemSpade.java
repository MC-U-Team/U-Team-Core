package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.fml.common.registry.*;

public class UItemSpade extends ItemSpade {
	
	public UItemSpade(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemSpade(String name, UCreativeTab tab, ToolMaterial material) {
		super(material);
		
		setRegistryName(USub.getID(), name);
		setUnlocalizedName(name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
	}
	
	private final void register() {
		ForgeRegistries.ITEMS.register(this);
	}
}
