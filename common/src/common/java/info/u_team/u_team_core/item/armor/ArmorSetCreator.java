package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.ItemProperties;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ArmorSetCreator {
	
	public static ArmorSet create(CommonRegister<Item> register, String name, Properties properties, ArmorMaterial material) {
		final RegistryEntry<UHelmetItem> helmet = register.register(name + "_helmet", () -> new UHelmetItem(name, new ItemProperties(properties), material));
		final RegistryEntry<UChestplateItem> chestplate = register.register(name + "_chestplate", () -> new UChestplateItem(name, new ItemProperties(properties), material));
		final RegistryEntry<ULeggingsItem> leggings = register.register(name + "_leggings", () -> new ULeggingsItem(name, new ItemProperties(properties), material));
		final RegistryEntry<UBootsItem> boots = register.register(name + "_boots", () -> new UBootsItem(name, new ItemProperties(properties), material));
		
		return new ArmorSet(helmet, chestplate, leggings, boots);
	}
}
