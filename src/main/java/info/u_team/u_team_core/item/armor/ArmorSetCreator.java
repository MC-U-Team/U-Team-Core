package info.u_team.u_team_core.item.armor;

import info.u_team.u_team_core.util.ItemProperties;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.fmllegacy.RegistryObject;

public class ArmorSetCreator {

	public static ArmorSet create(CommonDeferredRegister<Item> register, String name, Properties properties, ArmorMaterial material) {
		return create(register, name, null, properties, material);
	}

	public static ArmorSet create(CommonDeferredRegister<Item> register, String name, CreativeModeTab group, Properties properties, ArmorMaterial material) {
		final RegistryObject<UHelmetItem> helmet = register.register(name + "_helmet", () -> new UHelmetItem(name, group, new ItemProperties(properties), material));
		final RegistryObject<UChestplateItem> chestplate = register.register(name + "_chestplate", () -> new UChestplateItem(name, group, new ItemProperties(properties), material));
		final RegistryObject<ULeggingsItem> leggings = register.register(name + "_leggings", () -> new ULeggingsItem(name, group, new ItemProperties(properties), material));
		final RegistryObject<UBootsItem> boots = register.register(name + "_boots", () -> new UBootsItem(name, group, new ItemProperties(properties), material));

		return new ArmorSet(helmet, chestplate, leggings, boots);
	}
}
