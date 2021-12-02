package info.u_team.u_team_core.item.tier;

import info.u_team.u_team_core.api.item.ExtendedTier;
import info.u_team.u_team_core.util.ItemProperties;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.RegistryObject;

public class TierSetCreator {
	
	public static TierSet create(CommonDeferredRegister<Item> register, String name, Properties properties, ExtendedTier material) {
		return create(register, name, null, properties, material);
	}
	
	public static TierSet create(CommonDeferredRegister<Item> register, String name, CreativeModeTab creativeTab, Properties properties, ExtendedTier material) {
		final RegistryObject<UAxeItem> axe = register.register(name + "_axe", () -> new UAxeItem(creativeTab, new ItemProperties(properties), material));
		final RegistryObject<UHoeItem> hoe = register.register(name + "_hoe", () -> new UHoeItem(creativeTab, new ItemProperties(properties), material));
		final RegistryObject<UPickaxeItem> pickaxe = register.register(name + "_pickaxe", () -> new UPickaxeItem(creativeTab, new ItemProperties(properties), material));
		final RegistryObject<UShovelItem> shovel = register.register(name + "_shovel", () -> new UShovelItem(creativeTab, new ItemProperties(properties), material));
		final RegistryObject<USwordItem> sword = register.register(name + "_sword", () -> new USwordItem(creativeTab, new ItemProperties(properties), material));
		
		return new TierSet(axe, hoe, pickaxe, shovel, sword);
	}
	
}
