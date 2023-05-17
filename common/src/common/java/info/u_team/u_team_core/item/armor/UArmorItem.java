package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class UArmorItem extends ArmorItem implements UArmorItemExtension {
	
	protected final String textureName;
	
	public UArmorItem(String textureName, Properties properties, ArmorMaterial material, ArmorItem.Type type) {
		super(material, type, properties);
		this.textureName = textureName;
	}
	
	@Override
	public String resolveArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		if (!material.getName().equals("invalid")) {
			return null;
		}
		return String.format("%s:textures/models/armor/%s_layer_%d%s.png", RegistryUtil.getBuiltInRegistry(Registries.ITEM).getKey(this).getNamespace(), textureName, (slot == EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}
	
}
