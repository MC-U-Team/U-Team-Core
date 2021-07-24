package info.u_team.u_team_core.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Item.Properties;

public class UArmorItem extends ArmorItem {
	
	protected final String textureName;
	
	public UArmorItem(String textureName, Properties properties, IArmorMaterial material, EquipmentSlotType slot) {
		this(textureName, null, properties, material, slot);
	}
	
	public UArmorItem(String textureName, ItemGroup group, Properties properties, IArmorMaterial material, EquipmentSlotType slot) {
		super(material, slot, group == null ? properties : properties.tab(group));
		this.textureName = textureName;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if (!material.getName().equals("invalid")) {
			return null;
		}
		return String.format("%s:textures/models/armor/%s_layer_%d%s.png", getRegistryName().getNamespace(), textureName, (slot == EquipmentSlotType.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}
	
	protected String getTypeString(EquipmentSlotType slot) {
		switch (slot) {
		case HEAD:
			return "helmet";
		case CHEST:
			return "chestplate";
		case LEGS:
			return "leggings";
		case FEET:
			return "boots";
		default:
			return "invalid";
		}
	}
}
