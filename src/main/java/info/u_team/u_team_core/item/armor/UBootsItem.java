package info.u_team.u_team_core.item.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class UBootsItem extends UArmorItem {
	
	public UBootsItem(String name, Properties properties, IArmorMaterial material) {
		this(name, null, properties, material);
	}
	
	public UBootsItem(String name, ItemGroup group, Properties properties, IArmorMaterial material) {
		super(name, group, properties, material, EquipmentSlotType.FEET);
	}
	
}
