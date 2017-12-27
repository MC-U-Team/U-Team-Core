package info.u_team.u_team_core.item;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Armor Item
 * 
 * @date 27.12.17
 * @author HyCraftHD
 *
 */

public class UItemArmor extends ItemArmor {
	
	private String name, modid, typename, texturepath;
	
	public UItemArmor(ArmorMaterial material, int type, String typename, String name) {
		this(material, type, typename, name, null);
	}
	
	public UItemArmor(ArmorMaterial material, int type, String typename, String name, UCreativeTab tab) {
		super(material, -1, type);
		
		this.modid = USub.getID();
		this.name = name;
		this.typename = typename;
		this.texturepath = modid + ":textures/models/armor/" + name;
		setUnlocalizedName(name + "_" + typename);
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (slot < 0 || slot > 3) {
			return null;
		}
		
		if (slot == 2) {
			return texturepath + "_2.png";
		} else {
			return texturepath + "_1.png";
		}
	}
	
	private final void register() {
		GameRegistry.registerItem(this, name + "_" + typename);
	}
	
	public ResourceLocation getRegistryName() {
		return new ResourceLocation(modid, name + "_" + typename);
	}
	
	public void setTexturePath(String texturepath) {
		this.texturepath = texturepath;
	}
}
