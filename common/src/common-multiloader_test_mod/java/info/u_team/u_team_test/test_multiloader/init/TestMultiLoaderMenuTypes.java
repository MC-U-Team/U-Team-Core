package info.u_team.u_team_test.test_multiloader.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.menutype.UMenuType;
import info.u_team.u_team_test.test_multiloader.TestMultiLoaderReference;
import info.u_team.u_team_test.test_multiloader.menu.TestInventoryMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class TestMultiLoaderMenuTypes {
	
	public static final CommonRegister<MenuType<?>> MENU_TYPES = CommonRegister.create(Registries.MENU, TestMultiLoaderReference.MODID);
	
	public static final RegistryEntry<UMenuType<TestInventoryMenu>> TEST_INVENTORY = MENU_TYPES.register("test_inventory", () -> new UMenuType<>(TestInventoryMenu::new));
	
	static void register() {
		MENU_TYPES.register();
	}
	
}
