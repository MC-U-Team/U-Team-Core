package info.u_team.u_team_core.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;

public class ULeggingsItem extends UArmorItem {
	
	public ULeggingsItem(String textureName, Properties properties, ArmorMaterial material) {
		this(textureName, null, properties, material);
	}
	
	public ULeggingsItem(String textureName, CreativeModeTab group, Properties properties, ArmorMaterial material) {
		super(textureName, group, properties, material, EquipmentSlot.LEGS);
	}
	
}
