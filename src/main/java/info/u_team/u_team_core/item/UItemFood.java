package info.u_team.u_team_core.item;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Item API<br>
 * -> Basic Food Item
 * 
 * @date 27.12.17
 * @author HyCraftHD
 *
 */

public class UItemFood extends ItemFood {
	
	private String name, modid;
	
	public UItemFood(int amount, String name) {
		this(amount, name, null);
	}
	
	public UItemFood(int amount, String name, UCreativeTab tab) {
		this(amount, 0.6F, name, tab);
	}
	
	public UItemFood(int amount, float saturation, String name) {
		this(amount, saturation, name, null);
	}
	
	public UItemFood(int amount, float saturation, String name, UCreativeTab tab) {
		super(amount, saturation, false);
		
		this.modid = USub.getID();
		this.name = name;
		setUnlocalizedName(name);
		
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
