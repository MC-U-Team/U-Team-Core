package info.u_team.u_team_core.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class UArmorItem extends ArmorItem {

	protected final String textureName;

	public UArmorItem(String textureName, Properties properties, ArmorMaterial material, EquipmentSlot slot) {
		this(textureName, null, properties, material, slot);
	}

	public UArmorItem(String textureName, CreativeModeTab group, Properties properties, ArmorMaterial material, EquipmentSlot slot) {
		super(material, slot, group == null ? properties : properties.tab(group));
		this.textureName = textureName;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (!material.getName().equals("invalid")) {
			return null;
		}
		return String.format("%s:textures/models/armor/%s_layer_%d%s.png", getRegistryName().getNamespace(), textureName, (slot == EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}

	protected String getTypeString(EquipmentSlot slot) {
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
