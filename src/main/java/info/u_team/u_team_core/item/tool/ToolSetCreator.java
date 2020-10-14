package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.util.ItemProperties;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;

public class ToolSetCreator {
	
	public static ToolSet create(CommonDeferredRegister<Item> register, String name, Properties properties, IToolMaterial material) {
		return create(register, name, null, properties, material);
	}
	
	public static ToolSet create(CommonDeferredRegister<Item> register, String name, ItemGroup group, Properties properties, IToolMaterial material) {
		final RegistryObject<UAxeItem> axe = register.register(name + "_axe", () -> new UAxeItem(group, new ItemProperties(properties), material));
		final RegistryObject<UHoeItem> hoe = register.register(name + "_hoe", () -> new UHoeItem(group, new ItemProperties(properties), material));
		final RegistryObject<UPickaxeItem> pickaxe = register.register(name + "_pickaxe", () -> new UPickaxeItem(group, new ItemProperties(properties), material));
		final RegistryObject<UShovelItem> shovel = register.register(name + "_shovel", () -> new UShovelItem(group, new ItemProperties(properties), material));
		final RegistryObject<USwordItem> sword = register.register(name + "_sword", () -> new USwordItem(group, new ItemProperties(properties), material));
		
		return new ToolSet(axe, hoe, pickaxe, shovel, sword);
	}
	
}