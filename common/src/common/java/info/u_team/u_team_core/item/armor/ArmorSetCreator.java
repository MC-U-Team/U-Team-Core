package info.u_team.u_team_core.item.armor;

import java.util.function.Supplier;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.ItemProperties;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ArmorSetCreator {
	
	public static ArmorSet create(CommonRegister<Item> register, String name, Properties properties, Supplier<? extends Holder<ArmorMaterial>> material) {
		final RegistryEntry<UHelmetItem> helmet = register.register(name + "_helmet", () -> new UHelmetItem(new ItemProperties(properties), material.get()));
		final RegistryEntry<UChestplateItem> chestplate = register.register(name + "_chestplate", () -> new UChestplateItem(new ItemProperties(properties), material.get()));
		final RegistryEntry<ULeggingsItem> leggings = register.register(name + "_leggings", () -> new ULeggingsItem(new ItemProperties(properties), material.get()));
		final RegistryEntry<UBootsItem> boots = register.register(name + "_boots", () -> new UBootsItem(new ItemProperties(properties), material.get()));
		
		return new ArmorSet(helmet, chestplate, leggings, boots);
	}
}
