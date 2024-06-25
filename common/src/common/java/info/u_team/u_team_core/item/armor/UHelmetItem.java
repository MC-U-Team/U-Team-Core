package info.u_team.u_team_core.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class UHelmetItem extends UArmorItem {
	
	public UHelmetItem(Properties properties, Holder<ArmorMaterial> material) {
		super(properties, material, ArmorItem.Type.HELMET);
	}
	
}
