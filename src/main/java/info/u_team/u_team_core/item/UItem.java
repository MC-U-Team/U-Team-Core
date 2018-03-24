package info.u_team.u_team_core.item;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Item API<br>
 * -> Basic Item
 * 
 * @date 17.08.2017
 * @author MrTroble
 *
 */

public class UItem extends Item {
	
	private String name, modid;
	
	public UItem(String name) {
		this(name, null);
	}
	
	public UItem(String name, UCreativeTab tab) {
		super();
		
		this.modid = USub.getID();
		this.name = name;
		setUnlocalizedName(USub.getID() + ":" + name);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
	}
	
	private final void register() {
		GameRegistry.registerItem(this, name);
	}
	
	public ResourceLocation getRegistryName() {
		return new ResourceLocation(modid, name);
	}
}
