package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.api.registry.IURegistryType;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class UItemArmor extends ArmorItem implements IURegistryType {
	
	protected final String typeName;
	
	protected final String name;
	
	public UItemArmor(String name, Properties properties, IArmorMaterial material, EquipmentSlotType slot) {
		this(name, null, properties, material, slot);
	}
	
	public UItemArmor(String name, ItemGroup group, Properties properties, IArmorMaterial material, EquipmentSlotType slot) {
		super(material, slot, group == null ? properties : properties.group(group));
		this.typeName = name + "_" + getTypeString(slot);
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return typeName;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if (!material.getName().equals("invalid")) {
			return null;
		}
		return String.format("%s:textures/models/armor/%s_layer_%d%s.png", getRegistryName().getNamespace(), name, (slot == EquipmentSlotType.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
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
