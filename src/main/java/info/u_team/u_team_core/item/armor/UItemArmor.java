package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.api.*;
import info.u_team.u_team_core.api.registry.IUItem;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Item API<br>
 * -> Basic Armor Item
 * 
 * @date 27.12.17
 * @author HyCraftHD
 *
 */

public class UItemArmor extends ItemArmor implements IUItem, IModelProvider {
	
	protected String name;
	
	public UItemArmor(String name, ArmorMaterial material, EntityEquipmentSlot type, String typename) {
		this(name, null, material, type, typename);
	}
	
	public UItemArmor(String name, UCreativeTab tab, ArmorMaterial material, EntityEquipmentSlot type, String typename) {
		super(material, -1, type);
		
		this.name = name + "_" + typename;
		
		if (tab != null) {
			setCreativeTab(tab);
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (slot == EntityEquipmentSlot.HEAD || slot == EntityEquipmentSlot.CHEST || slot == EntityEquipmentSlot.FEET) {
			return getRegistryName().getResourceDomain() + ":textures/models/armor/" + name + "_1.png";
		} else if (slot == EntityEquipmentSlot.LEGS) {
			return getRegistryName().getResourceDomain() + ":textures/models/armor/" + name + "_2.png";
		} else {
			return null;
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
