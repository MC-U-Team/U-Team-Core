package info.u_team.u_team_core.item.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;

public class UItemChestplate extends UItemArmor {
	
	public UItemChestplate(String name, Properties properties, IArmorMaterial material) {
		this(name, null, properties, material);
	}
	
	public UItemChestplate(String name, ItemGroup group, Properties properties, IArmorMaterial material) {
		super(name, group, properties, material, EntityEquipmentSlot.CHEST);
	}
	
}
