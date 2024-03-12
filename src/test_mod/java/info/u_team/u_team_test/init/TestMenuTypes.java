package info.u_team.u_team_test.init;

import info.u_team.u_team_core.menutype.UMenuType;
import info.u_team.u_team_core.util.registry.CommonDeferredRegister;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.menu.BasicBlockEntityMenu;
import info.u_team.u_team_test.menu.BasicEnergyCreatorMenu;
import info.u_team.u_team_test.menu.BasicFluidInventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TestMenuTypes {
	
	public static final CommonDeferredRegister<MenuType<?>> MENU_TYPES = CommonDeferredRegister.create(ForgeRegistries.CONTAINERS, TestMod.MODID);
	
	public static final RegistryObject<UMenuType<BasicBlockEntityMenu>> BASIC_BLOCK_ENTITY = MENU_TYPES.register("basic_block_entity", () -> new UMenuType<>(BasicBlockEntityMenu::new));
	public static final RegistryObject<MenuType<BasicEnergyCreatorMenu>> BASIC_ENERGY_CREATOR = MENU_TYPES.register("energy_creator", () -> new UMenuType<>(BasicEnergyCreatorMenu::new));
	public static final RegistryObject<MenuType<BasicFluidInventoryMenu>> BASIC_FLUID_INVENTORY = MENU_TYPES.register("fluid_inventory", () -> new UMenuType<>(BasicFluidInventoryMenu::new));
	
	public static void registerMod(IEventBus bus) {
		MENU_TYPES.register(bus);
	}
}
