package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class TestMultiLoaderItems {
	
	public static final CommonRegister<Item> ITEMS = CommonRegister.create(Registries.ITEM, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<Item> TEST = ITEMS.register("test", () -> new Item(new Properties()));
	
	public static void register() {
		ITEMS.register();
	}
	
}
