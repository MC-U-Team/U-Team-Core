package info.u_team.u_team_core.item.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class UHelmetItem extends UArmorItem {
	
	public UHelmetItem(String textureName, Properties properties, IArmorMaterial material) {
		this(textureName, null, properties, material);
	}
	
	public UHelmetItem(String textureName, ItemGroup group, Properties properties, IArmorMaterial material) {
		super(textureName, group, properties, material, EquipmentSlotType.HEAD);
	}
	
}
