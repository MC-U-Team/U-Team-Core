package info.u_team.u_team_core.item.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class ULeggingsItem extends UArmorItem {
	
	public ULeggingsItem(String name, Properties properties, IArmorMaterial material) {
		this(name, null, properties, material);
	}
	
	public ULeggingsItem(String name, ItemGroup group, Properties properties, IArmorMaterial material) {
		super(name, group, properties, material, EquipmentSlotType.LEGS);
	}
	
}
