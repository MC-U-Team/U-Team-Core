package info.u_team.u_team_core.item.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;

public class UBootsItem extends UArmorItem {
	
	public UBootsItem(String textureName, Properties properties, IArmorMaterial material) {
		this(textureName, null, properties, material);
	}
	
	public UBootsItem(String textureName, ItemGroup group, Properties properties, IArmorMaterial material) {
		super(textureName, group, properties, material, EquipmentSlotType.FEET);
	}
	
}
