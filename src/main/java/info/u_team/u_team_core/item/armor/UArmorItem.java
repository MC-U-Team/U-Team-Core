package info.u_team.u_team_core.item.armor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class UArmorItem extends ArmorItem {
	
	protected final String textureName;
	
	public UArmorItem(String textureName, Properties properties, ArmorMaterial material, EquipmentSlot slot) {
		super(material, slot, properties);
		this.textureName = textureName;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (!material.getName().equals("invalid")) {
			return null;
		}
		return String.format("%s:textures/models/armor/%s_layer_%d%s.png", ForgeRegistries.ITEMS.getKey(this).getNamespace(), textureName, (slot == EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}
	
	protected String getTypeString(EquipmentSlot slot) {
		return switch (slot) {
		case HEAD -> "helmet";
		case CHEST -> "chestplate";
		case LEGS -> "leggings";
		case FEET -> "boots";
		default -> "invalid";
		};
	}
}
