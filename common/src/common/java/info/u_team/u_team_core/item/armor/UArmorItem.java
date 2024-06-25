package info.u_team.u_team_core.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class UArmorItem extends ArmorItem {
	
	public UArmorItem(Properties properties, Holder<ArmorMaterial> material, ArmorItem.Type type) {
		super(material, type, properties);
	}
	
}
