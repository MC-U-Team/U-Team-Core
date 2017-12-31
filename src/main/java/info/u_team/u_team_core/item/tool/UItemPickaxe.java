package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UItemPickaxe extends ItemPickaxe {
	
	private String name, modid;
	
	public UItemPickaxe(String name, ToolMaterial material) {
		this(name, null, material);
	}
	
	public UItemPickaxe(String name, UCreativeTab tab, ToolMaterial material) {
		super(material);
		
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
