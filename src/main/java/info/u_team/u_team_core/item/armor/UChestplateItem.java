package info.u_team.u_team_core.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;

import net.minecraft.world.item.Item.Properties;

public class UChestplateItem extends UArmorItem {
	
	public UChestplateItem(String textureName, Properties properties, ArmorMaterial material) {
		this(textureName, null, properties, material);
	}
	
	public UChestplateItem(String textureName, CreativeModeTab group, Properties properties, ArmorMaterial material) {
		super(textureName, group, properties, material, EquipmentSlot.CHEST);
	}
	
}
