package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.sub.USub;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
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
	
	private String texturepath;
	
	public UItemArmor(String name, ArmorMaterial material, EntityEquipmentSlot type, String typename) {
		this(name, null, material, type, typename);
	}
	
	public UItemArmor(String name, UCreativeTab tab, ArmorMaterial material, EntityEquipmentSlot type, String typename) {
		super(material, -1, type);
		
		setRegistryName(USub.getID(), name + "_" + typename);
		setUnlocalizedName(USub.getID() + ":" + name + "_" + typename);
		texturepath = USub.getID() + ":textures/models/armor/" + name;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
		register();
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (slot == EntityEquipmentSlot.HEAD || slot == EntityEquipmentSlot.CHEST || slot == EntityEquipmentSlot.FEET) {
			return texturepath + "_1.png";
		} else if (slot == EntityEquipmentSlot.LEGS) {
			return texturepath + "_2.png";
		} else {
			return null;
		}
	}
	
	public void setTexturepath(String texturepath) {
		this.texturepath = texturepath;
	}
	
	private final void register() {
		GameRegistry.register(this);
	}
	
}
