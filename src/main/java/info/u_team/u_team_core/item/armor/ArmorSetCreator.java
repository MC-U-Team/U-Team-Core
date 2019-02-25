package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.util.ItemProperties;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;

public class ArmorSetCreator {
	
	public static ArmorSet create(String name, Properties properties, IArmorMaterial material) {
		return create(name, null, properties, material);
	}
	
	public static ArmorSet create(String name, ItemGroup group, Properties properties, IArmorMaterial material) {
		return new ArmorSet(new UItemHelmet(name, group, new ItemProperties(properties), material), new UItemChestplate(name, group, new ItemProperties(properties), material), new UItemLeggings(name, group, new ItemProperties(properties), material), new UItemBoots(name, group, new ItemProperties(properties), material));
	}
}
