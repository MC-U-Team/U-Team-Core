package info.u_team.u_team_core.item.tier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.util.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class TierSetCreator {
	
	public static TierSet create(CommonRegister<Item> register, String name, Properties properties, ExtendedTier material) {
		final RegistryEntry<UAxeItem> axe = register.register(name + "_axe", () -> new UAxeItem(new ItemProperties(properties), material));
		final RegistryEntry<UHoeItem> hoe = register.register(name + "_hoe", () -> new UHoeItem(new ItemProperties(properties), material));
		final RegistryEntry<UPickaxeItem> pickaxe = register.register(name + "_pickaxe", () -> new UPickaxeItem(new ItemProperties(properties), material));
		final RegistryEntry<UShovelItem> shovel = register.register(name + "_shovel", () -> new UShovelItem(new ItemProperties(properties), material));
		final RegistryEntry<USwordItem> sword = register.register(name + "_sword", () -> new USwordItem(new ItemProperties(properties), material));
		
		return new TierSet(axe, hoe, pickaxe, shovel, sword);
	}
	
}
