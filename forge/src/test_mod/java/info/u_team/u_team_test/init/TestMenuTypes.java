package info.u_team.u_team_test.init;

import info.u_team.u_team_core.api.registry.CommonRegister;
import info.u_team.u_team_core.api.registry.RegistryEntry;
import info.u_team.u_team_core.menutype.UMenuType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.menu.BasicEnergyCreatorMenu;
import info.u_team.u_team_test.menu.BasicFluidInventoryMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class TestMenuTypes {
	
	public static final CommonRegister<MenuType<?>> MENU_TYPES = CommonRegister.create(Registries.MENU, TestMod.MODID);
	
	public static final RegistryEntry<MenuType<BasicEnergyCreatorMenu>> BASIC_ENERGY_CREATOR = MENU_TYPES.register("energy_creator", () -> new UMenuType<>(BasicEnergyCreatorMenu::new));
	public static final RegistryEntry<MenuType<BasicFluidInventoryMenu>> BASIC_FLUID_INVENTORY = MENU_TYPES.register("fluid_inventory", () -> new UMenuType<>(BasicFluidInventoryMenu::new));
	
	public static void register() {
		MENU_TYPES.register();
	}
}
