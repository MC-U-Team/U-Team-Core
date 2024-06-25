package info.u_team.u_team_core.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ULeggingsItem extends UArmorItem {
	
	public ULeggingsItem(Properties properties, Holder<ArmorMaterial> material) {
		super(properties, material, ArmorItem.Type.LEGGINGS);
	}
	
}
